package com.trendmicro.on_boarding_web_application.controller;

import com.trendmicro.on_boarding_web_application.entity.User;
import com.trendmicro.on_boarding_web_application.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor

public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        return ResponseEntity.status(201).body(userService.registerUser(user));
    }


}
