package com.lti.app.services;


import com.lti.app.dto.*;

import java.time.LocalDate;
import java.time.LocalTime;

public interface BookingService {

    /**
     * Books a workout slot for a user.
     * - Validates seat availability
     * - Ensures no overbooking (transactional consistency)
     * - Removes previous booking if user already has a booking
     *   for the same date & time (requirement #3)
     */
    BookingResponse bookSlot(BookingRequest request);

    /**
     * Cancels a user's booking.
     * - Frees a seat
     * - Promotes waitlisted user if available
     */
    CancelBookingResponse cancelBooking(Long bookingId, Long userId);

    /**
     * Returns availability info (booked seats,
     * remaining seats, waitlist count/status)
     * for a given center, date, and slot time.
     */
    AvailabilityResponse getAvailability(Long centerId, LocalDate date, LocalTime startTime);

    /**
     * Returns all bookings for a user for a given date.
     * Supports the bonus feature "view plan for the day".
     */
    UserBookingsResponse getUserBookings(Long userId, LocalDate date);

    /**
     * Checks and removes user's existing booking if it overlaps
     * with the new requested booking time.
     * Internally used by bookSlot() before final commit.
     */
    void removeConflictingBooking(Long userId, LocalDate date, LocalTime startTime);
}

