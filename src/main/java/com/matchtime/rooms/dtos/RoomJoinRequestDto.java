package com.matchtime.rooms.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RoomJoinRequestDto {
    private Long userId;
    private String inviteCode;
}
