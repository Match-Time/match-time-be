package com.example.matchtime.controller;

import com.example.matchtime.dto.UserUpdateRequest;
import com.example.matchtime.model.User;
import com.example.matchtime.repository.UserRepository;
import com.example.matchtime.service.RoomService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserRepository userRepository;
    private final RoomService roomService;

    public UserController(UserRepository userRepository, RoomService roomService) {
        this.userRepository = userRepository;
        this.roomService = roomService;
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return ResponseEntity.ok(userRepository.save(user));
    }

    @GetMapping("/users")
    public List<User> listUsers() {
        return userRepository.findAll();
    }

    @PutMapping("/users/{userId}")
    public ResponseEntity<User> updateUserNickname(@PathVariable Long userId,
                                                   @Valid @RequestBody UserUpdateRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));
        user.setNickname(request.getNickname());
        return ResponseEntity.ok(userRepository.save(user));
    }

    @GetMapping("/users/{userId}/rooms")
    public ResponseEntity<List<com.example.matchtime.model.Room>> listUserRooms(@PathVariable Long userId) {
        return ResponseEntity.ok(roomService.getRoomsForUser(userId));
    }
}
