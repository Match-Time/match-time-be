package com.matchtime.rooms.dtos;

import java.time.DayOfWeek;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ConfirmRequestDto {
    private String type;
    private DayOfWeek day;
    private String start;
    private String end;
    private String date;
}
