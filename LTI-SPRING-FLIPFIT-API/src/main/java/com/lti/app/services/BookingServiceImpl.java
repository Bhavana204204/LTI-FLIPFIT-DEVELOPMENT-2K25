package com.lti.app.services;


import com.lti.app.dto.*;
import com.lti.app.entity.*;
import com.lti.app.exception.SlotNotFoundException;
import com.lti.app.repository.BookingRepository;
import com.lti.app.repository.SlotRepository;
import com.lti.app.repository.WaitlistRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepo;
    private final WaitlistRepository waitlistRepo;
    private final SlotRepository slotRepo;

    @Override
    @Transactional
    public BookingResponse bookSlot(BookingRequest request) {
        // 1. load slot with DB row-lock to avoid race conditions
        Slot slot = slotRepo.findByIdForUpdate(request.getSlotInstanceId())
                .orElseThrow(() -> new SlotNotFoundException("Slot not found"));

        // 2. remove conflicting booking of same date/time for user (requirement #3)
        removeConflictingBooking(request.getUserId(), slot.getDate(), slot.getStartTime());

        // 3. verify slot state and seat availability
        if (slot.getStatus() == SlotStatus.CANCELLED || slot.getStatus() == SlotStatus.CLOSED) {
            return BookingResponse.failure("Slot is not available");
        }

        if (slot.getSeatsRemaining() > 0) {
            // decrement seats and persist
            slot.setSeatsRemaining(slot.getSeatsRemaining() - 1);
            slotRepo.save(slot); // within same transaction and lock

            Booking booking = Booking.builder()
                    .userId(request.getUserId())
                    .slotId(slot.getId())
                    .centerId(slot.getCenterId())
                    .status(BookingStatus.CONFIRMED)
                    .build();

            booking = bookingRepo.save(booking);

            return BookingResponse.success("Booking confirmed", booking.getId());
        }
        // 4. slot full -> add to waitlist (FIFO)
        Waitlist entry = Waitlist.builder()
                .userId(request.getUserId())
                .slotId(slot.getId())
                .status(WaitlistStatus.WAITING)
                .build();

        waitlistRepo.save(entry);
        return BookingResponse.waitlisted("Slot full. Added to waitlist");
    }

    @Override
    @Transactional
    public CancelBookingResponse cancelBooking(Long bookingId, Long userId) {
        Booking booking = bookingRepo.findById(bookingId)
                .orElseThrow(() -> new EntityNotFoundException("Booking not found"));

        if (!booking.getUserId().equals(userId)) {
            return CancelBookingResponse.failure("Unauthorized");
        }

        if (booking.getStatus() == BookingStatus.CANCELLED) {
            return CancelBookingResponse.failure("Booking already cancelled");
        }

        // cancel booking
        booking.setStatus(BookingStatus.CANCELLED);
        bookingRepo.save(booking);

        // unlock a seat: lock slot row
        Slot slot = slotRepo.findByIdForUpdate(booking.getSlotId())
                .orElseThrow(() -> new EntityNotFoundException("SlotInstance not found"));

        slot.setSeatsRemaining(slot.getSeatsRemaining() + 1);
        // Optionally change status from FULL -> OPEN
        if (slot.getStatus() == SlotStatus.FULL && slot.getSeatsRemaining() > 0) {
            slot.setStatus(SlotStatus.OPEN);
        }
        slotRepo.save(slot);

        // promote earliest waitlisted user (if any)
        promoteNextFromWaitlist(slot.getId());

        return CancelBookingResponse.success("Booking cancelled");
    }

    @Override
    @Transactional(readOnly = true)
    public AvailabilityResponse getAvailability(Long centerId, LocalDate date, LocalTime startTime) {
        Slot slot = slotRepo.findByCenterIdAndDateAndStartTime(centerId, date, startTime)
                .orElseThrow(() -> new EntityNotFoundException("Slot not found"));

        long waitlistCount = waitlistRepo.countBySlotId(slot.getId());
        int booked = slot.getCapacity() - slot.getSeatsRemaining();

        return AvailabilityResponse.builder()
                .capacity(slot.getCapacity())
                .booked(booked)
                .remaining(slot.getSeatsRemaining())
                .waitlistCount((int) waitlistCount)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public UserBookingsResponse getUserBookings(Long userId, LocalDate date) {
        List<Booking> bookings = bookingRepo.findByUserIdAndDate(userId, date);
        return UserBookingsResponse.fromBookings(bookings);
    }

    @Override
    @Transactional
    public void removeConflictingBooking(Long userId, LocalDate date, LocalTime startTime) {
        Optional<Booking> existing = bookingRepo.findOverlappingBooking(userId, date, startTime);
        existing.ifPresent(b -> {
            b.setStatus(BookingStatus.CANCELLED);
            bookingRepo.save(b);

            // free seat on the conflicting slot
            Slot conflictingSlot = slotRepo.findByIdForUpdate(b.getSlotId())
                    .orElseThrow(() -> new EntityNotFoundException("SlotInstance not found"));
            conflictingSlot.setSeatsRemaining(conflictingSlot.getSeatsRemaining() + 1);
            slotRepo.save(conflictingSlot);

            // promote waitlist for that slot
            promoteNextFromWaitlist(conflictingSlot.getId());
        });
    }

    // Promote first waiting user for a slot (kept transactional)
    private void promoteNextFromWaitlist(Long slotInstanceId) {
        List<Waitlist> waiting = waitlistRepo.findBySlotIdOrderByQueuedAt(slotInstanceId);
        if (waiting.isEmpty()) return;

        Waitlist next = waiting.get(0);

        // lock slot again before confirming
        Slot slot = slotRepo.findByIdForUpdate(slotInstanceId)
                .orElseThrow(() -> new EntityNotFoundException("SlotInstance not found"));

        if (slot.getSeatsRemaining() <= 0) {
            // nothing to promote (concurrent race). just return.
            return;
        }

        // consume a seat and create booking
        slot.setSeatsRemaining(slot.getSeatsRemaining() - 1);
        slotRepo.save(slot);

        Booking newBooking = Booking.builder()
                .userId(next.getUserId())
                .slotId(slot.getId())
                .centerId(slot.getCenterId())
                .status(BookingStatus.CONFIRMED)
                .build();

        bookingRepo.save(newBooking);

        // mark waitlist entry promoted
        next.setStatus(WaitlistStatus.PROMOTED);
        waitlistRepo.save(next);

        // (optionally) publish event/notification here
    }
}
