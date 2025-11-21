package com.matchtime.roomUsers;

import com.matchtime.rooms.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoomUserRepository extends JpaRepository<RoomUser, Long> {
    List<RoomUser> findByRoom(Room room);
    Optional<RoomUser> findByRoomIdAndUserId(Long roomId, Long userId);
}
