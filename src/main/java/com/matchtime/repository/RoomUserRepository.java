package com.matchtime.repository;

import com.matchtime.model.Room;
import com.matchtime.model.RoomUser;
import com.matchtime.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoomUserRepository extends JpaRepository<RoomUser, Long> {
    List<RoomUser> findByRoom(Room room);
    List<RoomUser> findByUser(User user);
    Optional<RoomUser> findByRoomAndUser(Room room, User user);
    void deleteByRoomAndUser(Room room, User user);
    boolean existsByRoomIdAndUserId(Long roomId, Long userId);
}