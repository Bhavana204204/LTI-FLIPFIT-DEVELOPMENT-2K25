package com.lti.app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AvailabilityResponse {
    private int capacity;
    private int booked;
    private int remaining;
}

