package com.trendmicro.on_boarding_web_application.service;

import com.trendmicro.on_boarding_web_application.model.RecaptchaResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class RecaptchaService {
    @Value("${recaptcha.secret-key}")
    private String secretKey;
    @Value("${recaptcha.verify-url}")
    private String verifyUrl;
    private final RestTemplate restTemplate;

    public RecaptchaResponse validateToken(String recaptchaToken) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String,String> map = new LinkedMultiValueMap<>();
        map.add("secret", secretKey);
        map.add("response",recaptchaToken);
        HttpEntity<MultiValueMap<String,String>> entity = new HttpEntity<>(map,headers);
        ResponseEntity<RecaptchaResponse> response = restTemplate.exchange(verifyUrl,
                HttpMethod.POST,
                entity,
                RecaptchaResponse.class);

        return response.getBody();
    }
}
