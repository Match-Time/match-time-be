package com.matchtime.model;

import jakarta.persistence.*;
import java.time.LocalTime;

@Entity
@Table(name = "weekly_unavailable")
public class WeeklyUnavailable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "weekday", nullable = false)
    private Weekday day;

    @Column(name = "start_time", nullable = false)
    private LocalTime start;

    @Column(name = "end_time", nullable = false)
    private LocalTime end;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public WeeklyUnavailable() {}

    public WeeklyUnavailable(Room room, User user, Weekday day,
                             LocalTime start, LocalTime end) {
        this.room = room;
        this.user = user;
        this.day = day;
        this.start = start;
        this.end = end;
    }

    // getters/setters 생략 가능
}
