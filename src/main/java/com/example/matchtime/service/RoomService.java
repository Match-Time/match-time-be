package com.example.matchtime.service;

import com.example.matchtime.dto.ConfirmRequest;
import com.example.matchtime.dto.RoomCreateRequest;
import com.example.matchtime.model.*;
import com.example.matchtime.repository.RoomRepository;
import com.example.matchtime.repository.RoomUserRepository;
import com.example.matchtime.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RoomService {

    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    private final RoomUserRepository roomUserRepository;

    public RoomService(RoomRepository roomRepository,
                       UserRepository userRepository,
                       RoomUserRepository roomUserRepository) {
        this.roomRepository = roomRepository;
        this.userRepository = userRepository;
        this.roomUserRepository = roomUserRepository;
    }

    @Transactional
    public Room createRoom(RoomCreateRequest request) {
        Assert.notNull(request, "request must not be null");
        String inviteCode = UUID.randomUUID().toString().substring(0, 8);
        Room room = new Room(request.getName(), request.getType(), inviteCode);
        return roomRepository.save(room);
    }

    public List<Room> listRooms() {
        return roomRepository.findAll();
    }

    public Room getRoom(Long roomId) {
        return roomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("Room not found: " + roomId));
    }

    public List<User> getUsersInRoom(Long roomId) {
        Room room = getRoom(roomId);
        return roomUserRepository.findByRoom(room)
                .stream()
                .map(RoomUser::getUser)
                .collect(Collectors.toList());
    }

    @Transactional
    public void addUserToRoom(Long userId, Long roomId) {
        Room room = getRoom(roomId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));

        Optional<RoomUser> existing = roomUserRepository.findByRoomAndUser(room, user);
        if (existing.isEmpty()) {
            roomUserRepository.save(new RoomUser(room, user));
        }
    }

    @Transactional
    public void removeUserFromRoom(Long userId, Long roomId) {
        Room room = getRoom(roomId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));
        roomUserRepository.deleteByRoomAndUser(room, user);

        // If no users remain, delete the room
        if (roomUserRepository.findByRoom(room).isEmpty()) {
            roomRepository.delete(room);
        }
    }

    @Transactional
    public void confirm(Long roomId, ConfirmRequest request) {
        Room room = getRoom(roomId);

        if (room.getType() != request.getType()) {
            throw new IllegalArgumentException("Room type mismatch");
        }

        if (room.getType() == RoomType.WEEKLY) {

            if (request.getDay() == null || request.getStart() == null || request.getEnd() == null) {
                throw new IllegalArgumentException("Weekly confirmation requires day, start, end");
            }

            LocalTime start = LocalTime.parse(request.getStart());
            LocalTime end = LocalTime.parse(request.getEnd());

            if (!end.isAfter(start)) {
                throw new IllegalArgumentException("end must be after start");
            }

            room.setConfirmedDay(request.getDay());
            room.setConfirmedStart(start);
            room.setConfirmedEnd(end);
            room.setConfirmedDate(null);

        } else {

            if (request.getDate() == null) {
                throw new IllegalArgumentException("Date must be provided");
            }

            LocalDate date = LocalDate.parse(request.getDate());
            room.setConfirmedDate(date);

            room.setConfirmedDay(null);
            room.setConfirmedStart(null);
            room.setConfirmedEnd(null);
        }

        roomRepository.save(room);
    }

    @Transactional
    public void deleteRoom(Long roomId) {
        Room room = getRoom(roomId);
        roomRepository.delete(room);
    }

    @Transactional
    public Room updateRoomName(Long roomId, String name) {
        Assert.hasText(name, "Room name must not be empty");
        Room room = getRoom(roomId);
        room.setName(name);
        return roomRepository.save(room);
    }

    @Transactional
    public void joinByInvite(Long userId, String inviteCode) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user"));

        Room room = roomRepository.findByInviteCode(inviteCode)
                .orElseThrow(() -> new IllegalArgumentException("Invalid invite code"));

        // 이미 참여 중이면 무시
        if (!roomUserRepository.existsByRoomIdAndUserId(room.getId(), userId)) {
            RoomUser ru = new RoomUser();
            ru.setRoom(room);
            ru.setUser(user);
            roomUserRepository.save(ru);
        }
    }

    @Transactional
    public List<Room> getRoomsForUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));

        return roomUserRepository.findByUser(user)
                .stream()
                .map(RoomUser::getRoom)
                .toList();
    }

}
