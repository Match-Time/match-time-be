package com.matchtime.dto;

import com.matchtime.model.RoomType;

import java.time.DayOfWeek;

public class ConfirmRequest {

    private RoomType type;
    private DayOfWeek day;
    private String start;
    private String end;
    private String date;

    public RoomType getType() { return type; }
    public DayOfWeek getDay() { return day; }
    public String getStart() { return start; }
    public String getEnd() { return end; }
    public String getDate() { return date; }
}
