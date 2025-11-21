package com.matchtime.users.dtos;


import com.matchtime.users.User;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserCreateRequestDto {

    @NotBlank
    private String nickname;

    @NotBlank
    private String email;

    @NotBlank
    private String password;

    public User toEntity(String encodedPassword) {
        return User.builder()
                .nickname(nickname)
                .email(email)
                .password(encodedPassword)
                .build();
    }
}
