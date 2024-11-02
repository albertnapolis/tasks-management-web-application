package com.trendmicro.on_boarding_web_application.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JwtIssuer {
    @Value("${security.jwt.secret-key}")
    private String secretKey;
    @Value("${security.jwt.expiration-time}")
    private long tokenExpiration;

    public String issue(Request request) {
        return JWT.create()
                .withSubject(String.valueOf(request.id))
                .withExpiresAt(new Date(System.currentTimeMillis() + tokenExpiration))
                .withClaim("username", request.username)
                .sign(Algorithm.HMAC256("secretKey"));
    }

    @Getter
    @Builder
    public static class Request {
        private final UUID id;
        private final String username;
    }

}
