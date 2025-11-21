package com.matchtime.dto;

import com.matchtime.model.RoomType;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

public class MatchResponse {

    private RoomType type;
    private List<WeeklyCandidate> weeklyCandidates;
    private List<String> dates;

    public MatchResponse(RoomType type) {
        this.type = type;
    }

    public RoomType getType() {
        return type;
    }

    public List<WeeklyCandidate> getWeeklyCandidates() {
        return weeklyCandidates;
    }

    public void setWeeklyCandidates(List<WeeklyCandidate> weeklyCandidates) {
        this.weeklyCandidates = weeklyCandidates;
    }

    public List<String> getDates() {
        return dates;
    }

    public void setDates(List<String> dates) {
        this.dates = dates;
    }

    // 🔥 너 코드에서 요구하는 형태로 정확히 맞춘 WeeklyCandidate
    public static class WeeklyCandidate {
        private DayOfWeek day;
        private LocalTime start;
        private LocalTime end;

        public WeeklyCandidate(DayOfWeek day, LocalTime start, LocalTime end) {
            this.day = day;
            this.start = start;
            this.end = end;
        }

        public DayOfWeek getDay() {
            return day;
        }

        public LocalTime getStart() {
            return start;
        }

        public LocalTime getEnd() {
            return end;
        }
    }
}
