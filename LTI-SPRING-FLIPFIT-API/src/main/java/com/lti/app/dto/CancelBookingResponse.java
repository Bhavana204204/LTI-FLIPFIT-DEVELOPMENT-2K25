package com.lti.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CancelBookingResponse {
    private String message;

    public static CancelBookingResponse success(String msg) {
        return new CancelBookingResponse(msg);
    }

    public static CancelBookingResponse failure(String msg) {
        return new CancelBookingResponse(msg);
    }
}
