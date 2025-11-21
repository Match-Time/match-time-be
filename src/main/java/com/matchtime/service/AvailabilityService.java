package com.matchtime.service;

import com.matchtime.dto.RecommendedDateResponse;
import com.matchtime.model.MonthlyUnavailable;
import com.matchtime.model.Room;
import com.matchtime.model.User;
import com.matchtime.repository.MonthlyUnavailableRepository;
import com.matchtime.repository.RoomRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AvailabilityService {

    private final RoomRepository roomRepository;
    private final RoomService roomService;
    private final MonthlyUnavailableRepository monthlyUnavailableRepository;

    public AvailabilityService(RoomRepository roomRepository,
                               RoomService roomService,
                               MonthlyUnavailableRepository monthlyUnavailableRepository) {
        this.roomRepository = roomRepository;
        this.roomService = roomService;
        this.monthlyUnavailableRepository = monthlyUnavailableRepository;
    }

    // 🔥 추천 날짜 계산
    @Transactional
    public List<RecommendedDateResponse> getRecommendedDates(Long roomId) {

        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("Room not found: " + roomId));

        List<User> users = roomService.getUsersInRoom(roomId);
        int totalUsers = users.size();
        // 추천 날짜는 2명 이상일 때만 의미 있게 제공
        if (totalUsers <= 1) return List.of();


        LocalDate today = LocalDate.now();
        LocalDate start = today; // 오늘 이전 날짜는 추천에서 제외
        LocalDate end = start.plusMonths(1).withDayOfMonth(1).plusMonths(1).minusDays(1);

        Map<LocalDate, Integer> availableCount = new HashMap<>();

        for (LocalDate date = start; !date.isAfter(end); date = date.plusDays(1)) {
            availableCount.put(date, totalUsers);
        }

        for (User user : users) {
            List<MonthlyUnavailable> blocks = monthlyUnavailableRepository.findByUser(user);

            for (MonthlyUnavailable mu : blocks) {
                LocalDate d = mu.getDate();
                if (availableCount.containsKey(d)) {
                    availableCount.put(d, availableCount.get(d) - 1);
                }
            }
        }

        List<RecommendedDateResponse> result =
                availableCount.entrySet().stream()
                        .filter(e -> e.getValue() > 0) // 모두 불가인 날짜는 제외
                        .filter(e -> !e.getKey().isBefore(today)) // 오늘 이전 제외
                        .map(e -> new RecommendedDateResponse(
                                e.getKey().toString(), e.getValue()
                        ))
                        .collect(Collectors.toList());

        // 정렬: 가능한 인원 DESC → 날짜 ASC
        result.sort((a, b) -> {
            if (b.getAvailableCount() != a.getAvailableCount()) {
                return b.getAvailableCount() - a.getAvailableCount();
            }
            return a.getDate().compareTo(b.getDate());
        });

        return result.stream().limit(3).collect(Collectors.toList());
    }
}
