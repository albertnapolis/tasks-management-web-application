package com.trendmicro.on_boarding_web_application.controller;

import com.trendmicro.on_boarding_web_application.model.LoginRequest;
import com.trendmicro.on_boarding_web_application.model.LoginResponse;
import com.trendmicro.on_boarding_web_application.security.JwtIssuer;
import com.trendmicro.on_boarding_web_application.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final JwtIssuer jwtIssuer;

    @PostMapping
    public LoginResponse login(@RequestBody LoginRequest request) {
        return authService.attemptLogin(request.getEmail(), request.getPassword());
    }
}
