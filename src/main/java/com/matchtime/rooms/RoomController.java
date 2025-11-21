package com.matchtime.rooms;

import com.matchtime.rooms.dtos.ConfirmRequestDto;
import com.matchtime.rooms.dtos.RoomCreateRequest;
import com.matchtime.rooms.dtos.RoomJoinRequestDto;
import com.matchtime.rooms.dtos.RoomUpdateRequestDto;
import com.matchtime.users.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class RoomController {

    private final RoomService roomService;

    @PostMapping("/rooms")
    public Room createRoom(@RequestBody RoomCreateRequest requestDto) {
        return roomService.createRoom(requestDto);
    }

    @GetMapping("/rooms/{roomId}")
    public Room getRoom(@PathVariable Long roomId) {
        return roomService.getRoom(roomId);
    }

    @PutMapping("/rooms/{roomId}")
    public Room updateRoomName(@PathVariable Long roomId, @RequestBody RoomUpdateRequestDto requestDto) {
        return roomService.updateRoomName(roomId, requestDto);
    }

    @GetMapping("/rooms")
    public List<Room> listRooms() {
        return roomService.listRooms();
    }

    @PostMapping("/users/{userId}/rooms/{roomId}")
    public ResponseEntity<Void> joinRoom(@PathVariable Long userId, @PathVariable Long roomId) {
        roomService.joinRoom(userId, roomId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/rooms/{roomId}/confirm")
    public ResponseEntity<Void> confirm(@PathVariable Long roomId, @RequestBody ConfirmRequestDto requestDto) {
        roomService.confirm(roomId, requestDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/join")
    public ResponseEntity<Void> joinWithInvite(@RequestBody RoomJoinRequestDto requestDto) {
        roomService.joinWithInvite(requestDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/rooms/{roomId}/users")
    public List<User> getRoomUsers(@PathVariable Long roomId) {
        return roomService.getRoomUsers(roomId);
    }

    @GetMapping("/rooms/{roomId}/recommend")
    public Object recommendDates(@PathVariable Long roomId) {
        return roomService.recommendDates(roomId);
    }

    @DeleteMapping("/rooms/{roomId}/users/{userId}")
    public ResponseEntity<Void> leaveRoom(@PathVariable Long roomId, @PathVariable Long userId) {
        roomService.leaveRoom(roomId, userId);
        return ResponseEntity.ok().build();
    }
}
