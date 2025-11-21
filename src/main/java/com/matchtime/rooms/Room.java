package com.matchtime.rooms;

import com.matchtime.roomUsers.RoomUser;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoomType type;

    @Column(nullable = false, unique = true)
    private String inviteCode;

    @Enumerated(EnumType.STRING)
    private DayOfWeek confirmedDay;

    private LocalTime confirmedStart;
    private LocalTime confirmedEnd;
    private LocalDate confirmedDate;

    @OneToMany(mappedBy = "room")
    @Builder.Default
    private List<RoomUser> roomUsers = new ArrayList<>();

    @PrePersist
    public void generateInviteCode() {
        if (this.inviteCode == null) {
            this.inviteCode = UUID.randomUUID().toString().substring(0, 8); // Generate a short UUID for invite code
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setConfirmedDay(DayOfWeek confirmedDay) {
        this.confirmedDay = confirmedDay;
    }

    public void setConfirmedStart(LocalTime confirmedStart) {
        this.confirmedStart = confirmedStart;
    }

    public void setConfirmedEnd(LocalTime confirmedEnd) {
        this.confirmedEnd = confirmedEnd;
    }

    public void setConfirmedDate(LocalDate confirmedDate) {
        this.confirmedDate = confirmedDate;
    }

    public enum RoomType {
        WEEKLY, ONCE
    }
}
