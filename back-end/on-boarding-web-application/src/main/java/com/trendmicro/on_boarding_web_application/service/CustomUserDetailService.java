package com.trendmicro.on_boarding_web_application.service;

import com.trendmicro.on_boarding_web_application.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private final UserService userService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userService.getUserByEmail(username);
        return UserPrincipal.builder()
                .id(user.getUserId())
                .username(user.getUsername())
                .password(user.getPassword())
                .build();
    }
}
