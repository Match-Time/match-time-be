package com.example.matchtime.dto;

import jakarta.validation.constraints.NotBlank;

public class UserUpdateRequest {

    @NotBlank
    private String nickname;

    public String getNickname() {
        return nickname;
    }
}
