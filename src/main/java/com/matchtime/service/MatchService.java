package com.matchtime.service;

import com.matchtime.dto.MatchResponse;
import com.matchtime.model.MonthlyUnavailable;
import com.matchtime.model.Room;
import com.matchtime.model.RoomUser;
import com.matchtime.model.User;
import com.matchtime.repository.MonthlyUnavailableRepository;
import com.matchtime.repository.RoomRepository;
import com.matchtime.repository.RoomUserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MatchService {

    private final RoomRepository roomRepository;
    private final RoomUserRepository roomUserRepository;
    private final MonthlyUnavailableRepository monthlyRepo;

    public MatchService(RoomRepository roomRepository,
                        RoomUserRepository roomUserRepository,
                        MonthlyUnavailableRepository monthlyRepo) {
        this.roomRepository = roomRepository;
        this.roomUserRepository = roomUserRepository;
        this.monthlyRepo = monthlyRepo;
    }

    /**
     * 개인 기반 불가능 날짜로 추천 날짜 계산
     */
    public MatchResponse computeMatch(Long roomId) {

        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("Room not found: " + roomId));

        // 방 사용자들 전체 조회
        List<RoomUser> participants = roomUserRepository.findByRoom(room);
        List<User> users = participants.stream()
                .map(RoomUser::getUser)
                .toList();

        // 이번 달 전체 날짜
        LocalDate start = LocalDate.now().withDayOfMonth(1);
        LocalDate end = start.plusMonths(1).minusDays(1);

        Map<LocalDate, Integer> availableCount = new HashMap<>();

        for (LocalDate d = start; !d.isAfter(end); d = d.plusDays(1)) {
            availableCount.put(d, users.size());
        }

        // 각 유저의 개인 불가 날짜 차감
        for (User user : users) {
            List<MonthlyUnavailable> userBlocks = monthlyRepo.findByUser(user);

            for (MonthlyUnavailable mu : userBlocks) {
                LocalDate d = mu.getDate();
                if (availableCount.containsKey(d)) {
                    availableCount.put(d, availableCount.get(d) - 1);
                }
            }
        }

        // 결과 DTO 생성
        MatchResponse response = new MatchResponse(room.getType());

        // 날짜별 가능한 인원이 정렬된 리스트
        List<Map.Entry<LocalDate, Integer>> sorted = availableCount.entrySet()
                .stream()
                .sorted((a, b) -> {
                    if (!Objects.equals(a.getValue(), b.getValue())) {
                        return b.getValue() - a.getValue(); // 가능한 인원 DESC
                    }
                    return a.getKey().compareTo(b.getKey()); // 날짜 ASC
                })
                .collect(Collectors.toList());

        // 날짜 문자열만 추출
        List<String> dateList = sorted.stream()
                .map(e -> e.getKey().toString() + " (가능 인원 " + e.getValue() + ")")
                .toList();

        response.setDates(dateList);

        return response;
    }
}
