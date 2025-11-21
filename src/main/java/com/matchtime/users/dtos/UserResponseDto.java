package com.matchtime.users.dtos;

import com.matchtime.users.User;
import lombok.Getter;

@Getter
public class UserResponseDto {

    final Long id;
    final String nickname;
    final String email;

    public UserResponseDto(User user) {
        this.id = user.getId();
        this.nickname = user.getNickname();
        this.email = user.getEmail();
    }
}
