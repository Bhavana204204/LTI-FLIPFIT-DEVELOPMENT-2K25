package com.lti.app.dto;

import com.lti.app.entity.Booking;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserBookingsResponse {

    private List<Booking> bookings;

    public static UserBookingsResponse fromBookings(List<Booking> list) {
        return new UserBookingsResponse(list);
    }
}

