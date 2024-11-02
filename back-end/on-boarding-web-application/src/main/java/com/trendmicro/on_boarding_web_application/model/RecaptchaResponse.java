package com.trendmicro.on_boarding_web_application.model;

public record RecaptchaResponse(Boolean success,String challege_ts,String hostname,Double score, String action) {
}
