package com.example.matchtime.controller;

import com.example.matchtime.dto.MonthlyUnavailableRequest;
import com.example.matchtime.service.PersonalAvailabilityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class PersonalAvailabilityController {

    private final PersonalAvailabilityService personalAvailabilityService;

    public PersonalAvailabilityController(PersonalAvailabilityService personalAvailabilityService) {
        this.personalAvailabilityService = personalAvailabilityService;
    }

    @PostMapping("/{userId}/unavailable/monthly")
    public ResponseEntity<?> saveUserUnavailableDates(
            @PathVariable Long userId,
            @RequestBody MonthlyUnavailableRequest request) {

        personalAvailabilityService.saveUserMonthlyUnavailable(userId, request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{userId}/unavailable/monthly")
    public ResponseEntity<?> getUserUnavailableDates(@PathVariable Long userId) {
        return ResponseEntity.ok(
                personalAvailabilityService.getUserMonthlyUnavailable(userId)
        );
    }
}
