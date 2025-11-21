package com.matchtime.dto;

import com.matchtime.model.RoomType;
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
