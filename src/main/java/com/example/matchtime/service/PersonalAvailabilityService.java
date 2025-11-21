package com.example.matchtime.service;

import com.example.matchtime.dto.MonthlyUnavailableRequest;
import com.example.matchtime.model.MonthlyUnavailable;
import com.example.matchtime.model.User;
import com.example.matchtime.repository.MonthlyUnavailableRepository;
import com.example.matchtime.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Service
public class PersonalAvailabilityService {

    private final UserRepository userRepo;
    private final MonthlyUnavailableRepository monthlyRepo;
    private final com.example.matchtime.repository.RoomRepository roomRepo;

    public PersonalAvailabilityService(UserRepository userRepo,
                                       MonthlyUnavailableRepository monthlyRepo,
                                       com.example.matchtime.repository.RoomRepository roomRepo) {
        this.userRepo = userRepo;
        this.monthlyRepo = monthlyRepo;
        this.roomRepo = roomRepo;
    }

    @Transactional
    public void saveUserMonthlyUnavailable(Long userId, MonthlyUnavailableRequest request) {

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));

        // 기존 데이터 삭제
        monthlyRepo.deleteByUser(user);

        // 새 값 저장
        if (request.getDates() != null) {
            Set<String> uniqueDates = new LinkedHashSet<>(request.getDates());
            for (String d : uniqueDates) {
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

    @Transactional
    public void saveRoomMonthlyUnavailable(Long userId, Long roomId, MonthlyUnavailableRequest request) {

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));

        com.example.matchtime.model.Room room = roomRepo.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("Room not found: " + roomId));

        monthlyRepo.deleteByUserAndRoom(user, room);

        if (request.getDates() != null) {
            Set<String> uniqueDates = new LinkedHashSet<>(request.getDates());
            for (String d : uniqueDates) {
                LocalDate date = LocalDate.parse(d);
                monthlyRepo.save(new MonthlyUnavailable(room, user, date));
            }
        }
    }

    @Transactional
    public List<String> getRoomMonthlyUnavailable(Long userId, Long roomId) {

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));

        com.example.matchtime.model.Room room = roomRepo.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("Room not found: " + roomId));

        return monthlyRepo.findByUserAndRoom(user, room)
                .stream()
                .map(mu -> mu.getDate().toString())
                .toList();
    }
}
