package com.example.matchtime.repository;

import com.example.matchtime.model.MonthlyUnavailable;
import com.example.matchtime.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MonthlyUnavailableRepository extends JpaRepository<MonthlyUnavailable, Long> {

    // 개인 전체 불가능 날짜 삭제
    void deleteByUser(User user);

    // 개인 불가능 날짜 조회
    List<MonthlyUnavailable> findByUser(User user);
    
}
