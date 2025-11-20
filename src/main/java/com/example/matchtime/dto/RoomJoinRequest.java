package com.example.matchtime.dto;

public class RoomJoinRequest {

    private Long userId;
    private String inviteCode;

    public Long getUserId() {
        return userId;
    }

    public String getInviteCode() {
        return inviteCode;
    }
}
