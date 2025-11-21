package com.matchtime.personalavailability;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UnavailableDateRepository extends JpaRepository<UnavailableDate, Long> {
    List<UnavailableDate> findByUserId(Long userId);
}
