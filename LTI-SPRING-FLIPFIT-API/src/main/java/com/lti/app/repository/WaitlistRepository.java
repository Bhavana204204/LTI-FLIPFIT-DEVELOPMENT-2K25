package com.lti.app.repository;


import com.lti.app.entity.Waitlist;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface WaitlistRepository extends JpaRepository<Waitlist, Long> {

    List<Waitlist> findBySlotIdOrderByQueuedAt(Long slotId);

    long countBySlotId(Long slotId);
}
