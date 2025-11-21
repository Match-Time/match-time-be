package com.matchtime.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

/**
 * Request body for saving a user's unavailability for a room.  Contains
 * a list of weekly time blocks (day + start/end) and a list of specific
 * dates.  When posted to the API, any existing unavailability for the
 * given room/user combination will be replaced entirely.
 */
public class AvailabilityRequest {

    @Valid
    private List<WeeklyBlock> weeklyUnavailable = new ArrayList<>();

    @NotNull
    private List<String> monthlyUnavailable = new ArrayList<>();

    public List<WeeklyBlock> getWeeklyUnavailable() {
        return weeklyUnavailable;
    }

    public void setWeeklyUnavailable(List<WeeklyBlock> weeklyUnavailable) {
        this.weeklyUnavailable = weeklyUnavailable;
    }

    public List<String> getMonthlyUnavailable() {
        return monthlyUnavailable;
    }

    public void setMonthlyUnavailable(List<String> monthlyUnavailable) {
        this.monthlyUnavailable = monthlyUnavailable;
    }

    /**
     * Represents a block of time in the user's weekly schedule during which
     * they are not available.  Times should be provided in HH:mm format.
     */
    public static class WeeklyBlock {
        @NotNull
        private DayOfWeek day;

        @NotNull
        private String start;

        @NotNull
        private String end;

        public WeeklyBlock() {
        }

        public WeeklyBlock(DayOfWeek day, String start, String end) {
            this.day = day;
            this.start = start;
            this.end = end;
        }

        public DayOfWeek getDay() {
            return day;
        }

        public void setDay(DayOfWeek day) {
            this.day = day;
        }

        public String getStart() {
            return start;
        }

        public void setStart(String start) {
            this.start = start;
        }

        public String getEnd() {
            return end;
        }

        public void setEnd(String end) {
            this.end = end;
        }
    }
}