package com.trendmicro.on_boarding_web_application.service;


import com.trendmicro.on_boarding_web_application.model.RecaptchaResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class RecaptchaFilter extends OncePerRequestFilter {

    private static final Logger LOG = LoggerFactory.getLogger(RecaptchaFilter.class);
    private final RecaptchaService recaptchaService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(request.getRequestURI().equals("/api/v1/auth") || request.getRequestURI().equals("/api/v1/users/register")) {
            String recaptcha = request.getHeader("recaptcha");
            RecaptchaResponse recaptchaResponse = recaptchaService.validateToken(recaptcha);
            if(!recaptchaResponse.success()) {
                LOG.info("Invalid reCAPTCHA token");
                throw new BadCredentialsException("Invalid reCaptcha token");
            }
        }
        filterChain.doFilter(request, response);
    }
}
