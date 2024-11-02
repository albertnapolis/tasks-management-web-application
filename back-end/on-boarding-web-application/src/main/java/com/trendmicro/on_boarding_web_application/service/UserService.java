package com.trendmicro.on_boarding_web_application.service;

import com.trendmicro.on_boarding_web_application.entity.User;
import com.trendmicro.on_boarding_web_application.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final TaskService taskService;

    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    private static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\|,.<>?/]).{8,20}$";

    public static boolean isValidEmail(String email) {
        return Pattern.matches(EMAIL_REGEX, email);
    }

    public static boolean isValidPassword(String password) {
        return Pattern.matches(PASSWORD_REGEX, password);
    }

    public static String encrypt(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public User registerUser(User user) {
        if(user.getEmail().isEmpty()) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(400), "email is empty");
        }
        if(user.getPassword().isEmpty()) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(400), "password is empty");
        }
        if(!isValidEmail(user.getEmail())) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(400), "invalid email");
        }
        if(!isValidPassword(user.getPassword())) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(400), "invalid password");
        }
        if(userRepository.findUserByUsername(user.getUsername()).isPresent()) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(400), "username is not available");
        }
        if(userRepository.findUserByEmail(user.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(400), "email is not available");
        }
        user.setPassword(encrypt(user.getPassword()));
        User savedUser = userRepository.save(user);
        taskService.assignMandatoryTasksToUser(savedUser.getUserId());
        return savedUser;
    }

    public User getUserByEmail(String email) {
        Optional<User> user = userRepository.findUserByEmail(email);
        if(user.isEmpty()) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404), "User not found");
        }
        return user.get();
    }
}
