package com.example.matchtime.dto;

import com.example.matchtime.model.RoomType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class RoomCreateRequest {

    @NotBlank
    private String name;

    @NotNull
    private RoomType type;

    public String getName() { return name; }
    public RoomType getType() { return type; }
}
