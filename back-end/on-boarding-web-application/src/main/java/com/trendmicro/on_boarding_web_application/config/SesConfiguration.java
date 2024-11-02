package com.trendmicro.on_boarding_web_application.config;

import com.amazonaws.auth.AWSSessionCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.MailSender;
import org.springframework.cloud.aws.mail.simplemail.SimpleEmailServiceMailSender;

@Configuration
public class SesConfiguration {

    @Value("${cloud.credentials.accessKeyId}")
    private String accessKeyId;
    @Value("${cloud.credentials.secretAccessKey}")
    private String secretAccessKey;
    @Value("${cloud.credentials.securityToken}")
    private String securityToken;

    @Bean
    public MailSender mailSender(AmazonSimpleEmailService amazonSimpleEmailService) {
        return new SimpleEmailServiceMailSender(amazonSimpleEmailService);
    }

    @Bean
    public AmazonSimpleEmailService amazonSimpleEmailService() {
       AWSSessionCredentials sessionCredentials = new BasicSessionCredentials(
                this.accessKeyId,
                this.secretAccessKey,
                this.securityToken
        );
        return AmazonSimpleEmailServiceClientBuilder
                .standard()
                .withRegion("us-east-1")
                .withCredentials(new AWSStaticCredentialsProvider(sessionCredentials))
                .build();
    }
}
