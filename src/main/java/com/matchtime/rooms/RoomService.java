package com.matchtime.rooms;

import com.matchtime.rooms.dtos.ConfirmRequestDto;
import com.matchtime.rooms.dtos.RoomCreateRequest;
import com.matchtime.rooms.dtos.RoomJoinRequestDto;
import com.matchtime.rooms.dtos.RoomUpdateRequestDto;
import com.matchtime.roomUsers.RoomUser;
import com.matchtime.roomUsers.RoomUserRepository;
import com.matchtime.users.User;
import com.matchtime.users.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;
    private final RoomUserRepository roomUserRepository;
    private final UserRepository userRepository;

    @Transactional
    public Room createRoom(RoomCreateRequest requestDto) {
        Room room = requestDto.toEntity();
        return roomRepository.save(room);
    }

    @Transactional(readOnly = true)
    public Room getRoom(Long roomId) {
        return roomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("해당 방이 없습니다. id=" + roomId));
    }

    @Transactional
    public Room updateRoomName(Long roomId, RoomUpdateRequestDto requestDto) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("해당 방이 없습니다. id=" + roomId));
        room.setName(requestDto.getName());
        return roomRepository.save(room);
    }

    @Transactional(readOnly = true)
    public List<Room> listRooms() {
        return roomRepository.findAll();
    }

    @Transactional
    public void joinRoom(Long userId, Long roomId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다. id=" + userId));
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("해당 방이 없습니다. id=" + roomId));

        RoomUser roomUser = RoomUser.builder()
                .user(user)
                .room(room)
                .build();

        roomUserRepository.save(roomUser);
    }

    @Transactional
    public void confirm(Long roomId, ConfirmRequestDto requestDto) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("해당 방이 없습니다. id=" + roomId));

        room.setConfirmedDay(requestDto.getDay());
        room.setConfirmedStart(LocalTime.parse(requestDto.getStart()));
        room.setConfirmedEnd(LocalTime.parse(requestDto.getEnd()));
        room.setConfirmedDate(LocalDate.parse(requestDto.getDate()));
        roomRepository.save(room);
    }

    @Transactional
    public void joinWithInvite(RoomJoinRequestDto requestDto) {
        User user = userRepository.findById(requestDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다. id=" + requestDto.getUserId()));
        Room room = roomRepository.findByInviteCode(requestDto.getInviteCode())
                .orElseThrow(() -> new IllegalArgumentException("해당 초대코드를 가진 방이 없습니다. inviteCode=" + requestDto.getInviteCode()));

        RoomUser roomUser = RoomUser.builder()
                .user(user)
                .room(room)
                .build();

        roomUserRepository.save(roomUser);
    }

    @Transactional(readOnly = true)
    public List<User> getRoomUsers(Long roomId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("해당 방이 없습니다. id=" + roomId));
        return roomUserRepository.findByRoom(room).stream()
                .map(RoomUser::getUser)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Object recommendDates(Long roomId) {
        // This is a placeholder. Actual implementation would involve complex logic
        // to find available dates based on user availabilities within the room.
        return "Recommendation logic to be implemented";
    }

    @Transactional
    public void leaveRoom(Long roomId, Long userId) {
        RoomUser roomUser = roomUserRepository.findByRoomIdAndUserId(roomId, userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 방에 유저가 존재하지 않습니다."));
        roomUserRepository.delete(roomUser);
    }
}
