package com.example.matchtime.controller;

import com.example.matchtime.dto.RoomJoinRequest;
import com.example.matchtime.dto.ConfirmRequest;
import com.example.matchtime.dto.RoomCreateRequest;
import com.example.matchtime.dto.RoomUpdateRequest;
import com.example.matchtime.model.Room;
import com.example.matchtime.model.User;
import com.example.matchtime.service.RoomService;
import com.example.matchtime.service.AvailabilityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RoomController {

    private final RoomService roomService;
    private final AvailabilityService availabilityService;

    public RoomController(RoomService roomService,
                          AvailabilityService availabilityService) {
        this.roomService = roomService;
        this.availabilityService = availabilityService;
    }

    @PostMapping("/rooms")
    public ResponseEntity<Room> createRoom(@Valid @RequestBody RoomCreateRequest request) {
        return ResponseEntity.ok(roomService.createRoom(request));
    }

    @GetMapping("/rooms")
    public List<Room> listRooms() {
        return roomService.listRooms();
    }

    @GetMapping("/rooms/{roomId}")
    public ResponseEntity<Room> getRoom(@PathVariable Long roomId) {
        return ResponseEntity.ok(roomService.getRoom(roomId));
    }

    @PutMapping("/rooms/{roomId}")
    public ResponseEntity<Room> updateRoomName(@PathVariable Long roomId,
                                               @Valid @RequestBody RoomUpdateRequest request) {
        return ResponseEntity.ok(roomService.updateRoomName(roomId, request.getName()));
    }

    @GetMapping("/rooms/{roomId}/users")
    public ResponseEntity<List<User>> getRoomUsers(@PathVariable Long roomId) {
        return ResponseEntity.ok(roomService.getUsersInRoom(roomId));
    }

    @PostMapping("/users/{userId}/rooms/{roomId}")
    public ResponseEntity<?> joinRoom(@PathVariable Long userId, @PathVariable Long roomId) {
        roomService.addUserToRoom(userId, roomId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/rooms/{roomId}/users/{userId}")
    public ResponseEntity<?> leaveRoom(@PathVariable Long roomId, @PathVariable Long userId) {
        roomService.removeUserFromRoom(userId, roomId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/rooms/{roomId}")
    public ResponseEntity<?> deleteRoom(@PathVariable Long roomId) {
        roomService.deleteRoom(roomId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/rooms/{roomId}/confirm")
    public ResponseEntity<?> confirm(@PathVariable Long roomId,
                                     @RequestBody ConfirmRequest request) {
        roomService.confirm(roomId, request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/join")
    public ResponseEntity<?> joinWithInvite(@RequestBody RoomJoinRequest request) {
        roomService.joinByInvite(request.getUserId(), request.getInviteCode());
        return ResponseEntity.ok().build();
    }

    // ✔ 추천 날짜 API만 유지
    @GetMapping("/rooms/{roomId}/recommend")
    public ResponseEntity<?> recommendDates(@PathVariable Long roomId) {
        return ResponseEntity.ok(availabilityService.getRecommendedDates(roomId));
    }
}
