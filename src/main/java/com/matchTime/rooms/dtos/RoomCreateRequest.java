package com.matchtime.rooms.dtos;

import com.matchtime.rooms.Room;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RoomCreateRequest {

    @NotBlank
    private String name;
    private String type; // Added type field

    public Room toEntity() {
        return Room.builder()
                .name(name)
                .type(Room.RoomType.valueOf(type)) // Assuming RoomType is an enum in Room entity
                .build();
    }
}
