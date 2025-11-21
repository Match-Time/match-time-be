package com.matchtime.rooms.dtos;

import com.matchtime.rooms.Room;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
public class RoomResponseDto {

    private final Long id;
    private final String name;
    private final Room.RoomType type;
    private final String inviteCode;
    private final java.time.DayOfWeek confirmedDay;
    private final LocalTime confirmedStart;
    private final LocalTime confirmedEnd;
    private final LocalDate confirmedDate;

    public RoomResponseDto(Room room) {
        this.id = room.getId();
        this.name = room.getName();
        this.type = room.getType();
        this.inviteCode = room.getInviteCode();
        this.confirmedDay = room.getConfirmedDay();
        this.confirmedStart = room.getConfirmedStart();
        this.confirmedEnd = room.getConfirmedEnd();
        this.confirmedDate = room.getConfirmedDate();
    }
}
