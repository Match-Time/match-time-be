package com.matchtime.dto;

import jakarta.validation.constraints.NotBlank;

public class RoomUpdateRequest {

    @NotBlank
    private String name;

    public String getName() {
        return name;
    }
}
