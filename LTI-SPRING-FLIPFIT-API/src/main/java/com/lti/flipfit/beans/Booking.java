package com.lti.flipfit.beans;


public class Booking {
	private Long bookingId;
	private Long userId;
	private Long scheduledId;
	private String status;
	
	
	public Booking(Long bookingId, Long userId, Long scheduledId, String status) {
		super();
		this.bookingId = bookingId;
		this.userId = userId;
		this.scheduledId = scheduledId;
		this.status = status;
	}
	public Long getBookingId() {
		return bookingId;
	}
	public void setBookingId(Long bookingId) {
		this.bookingId = bookingId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getScheduledId() {
		return scheduledId;
	}
	public void setScheduledId(Long scheduledId) {
		this.scheduledId = scheduledId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
