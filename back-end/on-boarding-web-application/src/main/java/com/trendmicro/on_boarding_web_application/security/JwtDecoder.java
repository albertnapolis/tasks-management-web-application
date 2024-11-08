package com.trendmicro.on_boarding_web_application.security;

import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.auth0.jwt.JWT;

@Component
@RequiredArgsConstructor
public class JwtDecoder {
    @Value("${security.jwt.secret-key}")
    private String secretKey;

    public DecodedJWT decode(String token) {
        return JWT.require(Algorithm.HMAC256("secretKey"))
                .build()
                .verify(token);
    }
}
