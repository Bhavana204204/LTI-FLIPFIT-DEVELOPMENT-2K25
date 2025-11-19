package com.lti.app.entity;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "slots",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"center_id", "date", "start_time"})
        }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Slot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Which center the slot belongs to
    @Column(name = "center_id", nullable = false)
    private Long centerId;

    // Date of workout (2025-02-01)
    @Column(nullable = false)
    private LocalDate date;

    // Start time (e.g., 06:00)
    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    // End time (e.g., 07:00)
    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;

    // Total seats configured by gym owner
    @Column(nullable = false)
    private Integer capacity;

    // Real-time seats left
    @Column(name = "seats_remaining", nullable = false)
    private Integer seatsRemaining;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SlotStatus status;   // OPEN, FULL, CANCELLED, CLOSED

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;
}

