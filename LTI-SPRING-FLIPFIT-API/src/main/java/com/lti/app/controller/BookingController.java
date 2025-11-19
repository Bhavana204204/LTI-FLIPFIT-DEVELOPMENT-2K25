package com.lti.app.controller;


import com.lti.app.dto.*;
import com.lti.app.services.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;


    @PostMapping
    public ResponseEntity<BookingResponse> bookSlot(@RequestBody BookingRequest request) {
        BookingResponse response = bookingService.bookSlot(request);
        return ResponseEntity.ok(response);
    }


    @DeleteMapping("/{bookingId}")
    public ResponseEntity<CancelBookingResponse> cancelBooking(@PathVariable Long bookingId, @RequestParam Long userId) {
        CancelBookingResponse response = bookingService.cancelBooking(bookingId, userId);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/availability")
    public ResponseEntity<AvailabilityResponse> getAvailability(
            @RequestParam Long centerId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime startTime
    ) {
        AvailabilityResponse response =
                bookingService.getAvailability(centerId, date, startTime);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/user")
    public ResponseEntity<UserBookingsResponse> getUserBookings(
            @RequestParam Long userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        UserBookingsResponse response =
                bookingService.getUserBookings(userId, date);
        return ResponseEntity.ok(response);
    }
}
