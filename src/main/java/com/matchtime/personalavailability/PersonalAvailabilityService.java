package com.matchtime.personalavailability;

import com.matchtime.personalavailability.dtos.MonthlyUnavailableRequestDto;
import com.matchtime.users.User;
import com.matchtime.users.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PersonalAvailabilityService {

    private final UnavailableDateRepository unavailableDateRepository;
    private final UserRepository userRepository;

    @Transactional
    public void saveUserUnavailableDates(Long userId, MonthlyUnavailableRequestDto requestDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));

        List<UnavailableDate> unavailableDates = requestDto.getDates().stream()
                .map(dateStr -> UnavailableDate.builder()
                        .user(user)
                        .date(LocalDate.parse(dateStr))
                        .build())
                .collect(Collectors.toList());

        unavailableDateRepository.saveAll(unavailableDates);
    }

    @Transactional(readOnly = true)
    public List<String> getUserUnavailableDates(Long userId) {
        return unavailableDateRepository.findByUserId(userId).stream()
                .map(UnavailableDate::getDate)
                .map(LocalDate::toString)
                .collect(Collectors.toList());
    }
}
