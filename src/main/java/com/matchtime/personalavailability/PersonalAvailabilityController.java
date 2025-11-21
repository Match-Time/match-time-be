package com.matchtime.personalavailability;

import com.matchtime.personalavailability.dtos.MonthlyUnavailableRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users/{userId}/unavailable/monthly")
public class PersonalAvailabilityController {

    private final PersonalAvailabilityService personalAvailabilityService;

    @PostMapping
    public ResponseEntity<Void> saveUserUnavailableDates(@PathVariable Long userId,
                                                         @RequestBody MonthlyUnavailableRequestDto requestDto) {
        personalAvailabilityService.saveUserUnavailableDates(userId, requestDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Map<String, List<String>>> getUserUnavailableDates(@PathVariable Long userId) {
        List<String> dates = personalAvailabilityService.getUserUnavailableDates(userId);
        return ResponseEntity.ok(Map.of("dates", dates));
    }
}
