package com.trendmicro.on_boarding_web_application.service;

import com.trendmicro.on_boarding_web_application.model.LoginResponse;
import com.trendmicro.on_boarding_web_application.security.JwtIssuer;
import com.trendmicro.on_boarding_web_application.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtIssuer jwtIssuer;
    private final AuthenticationManager authenticationManager;

    public LoginResponse attemptLogin(String email, String password) {
        if(email.isEmpty()) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(400), "email is empty");
        }
        if(password.isEmpty()) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(400), "password is empty");
        }
        var authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        var principal = (UserPrincipal) authentication.getPrincipal();

        var token = jwtIssuer.issue(JwtIssuer.Request.builder()
                .id(principal.getId())
                .username(principal.getUsername())
                .build());

        return LoginResponse.builder()
                .accessToken(token)
                .build();
    }
}
