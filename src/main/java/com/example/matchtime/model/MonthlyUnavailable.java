package com.example.matchtime.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "monthly_unavailable")
public class MonthlyUnavailable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ✔ 개인 기반에서는 room이 없어도 됨 → nullable 허용
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = true)
    private Room room;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private LocalDate date;

    public MonthlyUnavailable() {}

    // ✔ room이 null이어도 저장 가능해야 함
    public MonthlyUnavailable(Room room, User user, LocalDate date) {
        this.room = room;
        this.user = user;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public Room getRoom() {
        return room;
    }

    public User getUser() {
        return user;
    }

    public LocalDate getDate() {
        return date;
    }
}
