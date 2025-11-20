package com.example.matchtime.service;

import com.example.matchtime.dto.MonthlyUnavailableRequest;
import com.example.matchtime.model.MonthlyUnavailable;
import com.example.matchtime.model.User;
import com.example.matchtime.repository.MonthlyUnavailableRepository;
import com.example.matchtime.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PersonalAvailabilityService {

    private final UserRepository userRepo;
    private final MonthlyUnavailableRepository monthlyRepo;

    public PersonalAvailabilityService(UserRepository userRepo,
                                       MonthlyUnavailableRepository monthlyRepo) {
        this.userRepo = userRepo;
        this.monthlyRepo = monthlyRepo;
    }

    @Transactional
    public void saveUserMonthlyUnavailable(Long userId, MonthlyUnavailableRequest request) {

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));

        // 기존 데이터 삭제
        monthlyRepo.deleteByUser(user);

        // 새 값 저장
        if (request.getDates() != null) {
            for (String d : request.getDates()) {
                LocalDate date = LocalDate.parse(d);
                monthlyRepo.save(new MonthlyUnavailable(null, user, date));
            }
        }
    }

    @Transactional
    public List<String> getUserMonthlyUnavailable(Long userId) {

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));

        return monthlyRepo.findByUser(user)
                .stream()
                .map(mu -> mu.getDate().toString())
                .toList();
    }
}
