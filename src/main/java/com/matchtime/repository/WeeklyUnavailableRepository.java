package com.matchtime.repository;

import com.matchtime.model.Room;
import com.matchtime.model.User;
import com.matchtime.model.WeeklyUnavailable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.DayOfWeek;
import java.util.List;

public interface WeeklyUnavailableRepository extends JpaRepository<WeeklyUnavailable, Long> {

    List<WeeklyUnavailable> findByRoomAndDay(Room room, DayOfWeek day);

    // 삭제 기능 필요함
    void deleteByRoomAndUser(Room room, User user);
}
