package com.lti.app.repository;


import com.lti.app.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    Optional<Booking> findByUserIdAndSlotId(Long userId, Long slotId);
    List<Booking> findByUserId(Long userId);
    Optional<Booking> findByUserIdAndBookedAtBetween(Long userId, LocalDateTime start, LocalDateTime end);

    @Query("""
       SELECT b 
       FROM Booking b 
       JOIN Slot s ON b.slotId = s.id
       WHERE b.userId = :userId 
         AND s.date = :date 
         AND b.status = 'CONFIRMED'
       """)
    List<Booking> findByUserIdAndDate(@Param("userId") Long userId,
                                      @Param("date") LocalDate date);
    @Query("""
       SELECT b 
       FROM Booking b 
       JOIN Slot s ON b.slotId = s.id
       WHERE b.userId = :userId
         AND s.date = :date
         AND s.startTime = :startTime
         AND b.status = 'CONFIRMED'
       """)
    Optional<Booking> findOverlappingBooking(@Param("userId") Long userId,
                                             @Param("date") LocalDate date,
                                             @Param("startTime") LocalTime startTime);


}
