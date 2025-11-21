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

    @PutMapping("/{userId}/unavailable/monthly")
    public ResponseEntity<?> replaceUserUnavailableDates(
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

    @PostMapping("/{userId}/rooms/{roomId}/unavailable/monthly")
    public ResponseEntity<?> saveRoomUnavailableDates(
            @PathVariable Long userId,
            @PathVariable Long roomId,
            @RequestBody MonthlyUnavailableRequest request) {

        personalAvailabilityService.saveRoomMonthlyUnavailable(userId, roomId, request);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{userId}/rooms/{roomId}/unavailable/monthly")
    public ResponseEntity<?> replaceRoomUnavailableDates(
            @PathVariable Long userId,
            @PathVariable Long roomId,
            @RequestBody MonthlyUnavailableRequest request) {

        personalAvailabilityService.saveRoomMonthlyUnavailable(userId, roomId, request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{userId}/rooms/{roomId}/unavailable/monthly")
    public ResponseEntity<?> getRoomUnavailableDates(
            @PathVariable Long userId,
            @PathVariable Long roomId) {
        return ResponseEntity.ok(
                personalAvailabilityService.getRoomMonthlyUnavailable(userId, roomId)
        );
    }
}
