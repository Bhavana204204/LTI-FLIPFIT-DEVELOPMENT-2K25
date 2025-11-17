package com.lti.flipfit.services;

import java.util.List;

import com.lti.flipfit.beans.Booking;
import com.lti.flipfit.beans.GymSlot;

public interface GymCustomerService {
	
	public List<Booking> viewBookings();
	
	public Booking cancelBooking(Long bookingId);
	
	public Booking getBookingDetails(Long bookingId);
	
	public Booking bookSlot(Booking booking);

}
