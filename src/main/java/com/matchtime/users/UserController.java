package com.matchtime.users;

import com.matchtime.users.dtos.UserCreateRequestDto;
import com.matchtime.users.dtos.UserResponseDto;
import com.matchtime.users.dtos.UserUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public User createUser(@RequestBody UserCreateRequestDto requestDto) {
        return userService.save(requestDto);
    }

    @GetMapping
    public List<User> listUsers() {
        return userService.findAll();
    }

    @PutMapping("/{userId}")
    public User updateUserNickname(@PathVariable Long userId, @RequestBody UserUpdateRequestDto requestDto) {
        return userService.updateUserNickname(userId, requestDto.getNickname());
    }
}
