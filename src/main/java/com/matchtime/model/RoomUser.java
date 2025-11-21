package com.matchtime.model;

import jakarta.persistence.*;

@Entity
@Table(name = "room_users",
        uniqueConstraints = @UniqueConstraint(columnNames = {"room_id", "user_id"}))
public class RoomUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public RoomUser() {}

    public RoomUser(Room room, User user) {
        this.room = room;
        this.user = user;
    }

    public Long getId() { return id; }

    public Room getRoom() { return room; }

    public void setRoom(Room room) { this.room = room; }

    public User getUser() { return user; }

    public void setUser(User user) { this.user = user; }
}
