package com.example.matchtime.repository;

import com.example.matchtime.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {

    Optional<Room> findByInviteCode(String inviteCode);
}
