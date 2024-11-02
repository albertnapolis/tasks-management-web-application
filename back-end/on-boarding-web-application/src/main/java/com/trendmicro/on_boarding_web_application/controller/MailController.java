package com.trendmicro.on_boarding_web_application.controller;

import com.trendmicro.on_boarding_web_application.service.MailService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/email")
@RequiredArgsConstructor
public class MailController {

    @Autowired
    private MailService mailService;

    @PostMapping("/sendmail")
    public ResponseEntity<String> sendMessage(@RequestBody EmailRequest emailRequest) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("albert_napolis@trendmicro.com");
        simpleMailMessage.setTo(emailRequest.getToEmail());
        simpleMailMessage.setSubject(emailRequest.getSubject());
        simpleMailMessage.setText(emailRequest.getBody());
        mailService.sendMessage(simpleMailMessage);
        return ResponseEntity.status(200).body("Mail Send Successfully");
    }

    @Data
    public static class EmailRequest {
        private String toEmail;
        private String subject;
        private String body;
    }
}
