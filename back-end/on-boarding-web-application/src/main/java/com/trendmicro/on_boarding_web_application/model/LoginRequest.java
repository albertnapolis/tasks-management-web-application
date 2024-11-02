package com.trendmicro.on_boarding_web_application.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginRequest {
    public final String email;
    public final String password;
}
