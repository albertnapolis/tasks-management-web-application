package com.trendmicro.on_boarding_web_application.security;

import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class JwtToPrincipalConverter {
    public UserPrincipal convert(DecodedJWT jwt) {
        return UserPrincipal.builder()
                .id(UUID.fromString(jwt.getSubject()))
                .username(jwt.getClaim("username").asString())
                .build();
    }
}
