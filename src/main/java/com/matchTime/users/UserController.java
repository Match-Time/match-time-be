package com.matchTime.users;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }
}
