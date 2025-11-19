package com.lti.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingResponse {
    private String message;
    private Long bookingId;
    private boolean waitlisted;

    public static BookingResponse success(String msg, Long id) {
        return new BookingResponse(msg, id, false);
    }

    public static BookingResponse failure(String msg) {
        return new BookingResponse(msg, null, false);
    }

    public static BookingResponse waitlisted(String msg) {
        return new BookingResponse(msg, null, true);
    }
}
