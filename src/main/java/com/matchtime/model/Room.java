package com.matchtime.model;

import jakarta.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "rooms")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
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

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore // prevent infinite recursion when serializing Room -> RoomUser -> Room
    private List<RoomUser> users = new ArrayList<>();

    public Room() {}

    public Room(String name, RoomType type, String inviteCode) {
        this.name = name;
        this.type = type;
        this.inviteCode = inviteCode;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public RoomType getType() { return type; }
    public String getInviteCode() { return inviteCode; }
    public DayOfWeek getConfirmedDay() { return confirmedDay; }
    public LocalTime getConfirmedStart() { return confirmedStart; }
    public LocalTime getConfirmedEnd() { return confirmedEnd; }
    public LocalDate getConfirmedDate() { return confirmedDate; }

    public void setName(String name) { this.name = name; }
    public void setType(RoomType type) { this.type = type; }
    public void setInviteCode(String inviteCode) { this.inviteCode = inviteCode; }
    public void setConfirmedDay(DayOfWeek confirmedDay) { this.confirmedDay = confirmedDay; }
    public void setConfirmedStart(LocalTime confirmedStart) { this.confirmedStart = confirmedStart; }
    public void setConfirmedEnd(LocalTime confirmedEnd) { this.confirmedEnd = confirmedEnd; }
    public void setConfirmedDate(LocalDate confirmedDate) { this.confirmedDate = confirmedDate; }
}
