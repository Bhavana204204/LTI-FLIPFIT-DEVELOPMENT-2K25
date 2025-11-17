package com.lti.flipfit.services;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import com.lti.flipfit.beans.Booking;
import com.lti.flipfit.beans.GymSlot;

@Service
public class GymCustomerServiceImpl implements GymCustomerService {
	List<Booking> bookings = new ArrayList<>();

	public GymCustomerServiceImpl() {
		bookings.add(new Booking(Long.valueOf(1), Long.valueOf(1), Long.valueOf(1), "Successful"));
		bookings.add(new Booking(Long.valueOf(2), Long.valueOf(2), Long.valueOf(2), "Successful"));
		bookings.add(new Booking(Long.valueOf(3), Long.valueOf(3), Long.valueOf(3), "Successful"));
		bookings.add(new Booking(Long.valueOf(4), Long.valueOf(4), Long.valueOf(4), "Cancelled"));
		bookings.add(new Booking(Long.valueOf(5), Long.valueOf(5), Long.valueOf(5), "Successful"));
	}

	@Override
	public List<Booking> viewBookings() {
		return bookings;
	}

	@Override
	public Booking cancelBooking(Long bookingId) {
		if (bookingId == null) {
			throw new IllegalArgumentException("bookingId must not be null");
		}

		Booking booking = bookings.stream().filter(b -> bookingId.equals(b.getBookingId())).findAny()
				.orElseThrow(() -> new NoSuchElementException("No booking found for id: " + bookingId));

		booking.setStatus("Cancelled");
		return booking;
	}

	@Override
	public Booking getBookingDetails(Long bookingId) {
		if (bookingId == null) {
			throw new IllegalArgumentException("bookingId must not be null");
		}

		Booking booking = bookings.stream().filter(b -> bookingId.equals(b.getBookingId())).findAny()
				.orElseThrow(() -> new NoSuchElementException("No booking found for id: " + bookingId));
		return booking;
	}

	@Override
	public Booking bookSlot(Booking booking) {
		bookings.add(booking);
		return booking;
	}

}
