package com.lti.app.dto;


import lombok.Data;

@Data
public class BookingRequest {
    private Long userId;
    private Long centerId;
    private Long slotInstanceId;
}
