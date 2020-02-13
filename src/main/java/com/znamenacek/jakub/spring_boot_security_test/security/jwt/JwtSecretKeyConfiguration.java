package com.znamenacek.jakub.spring_boot_security_test.security.jwt;

import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;


@Configuration
public class JwtSecretKeyConfiguration {
    private final JwtConfiguration jwtConfiguration;

    @Autowired
    public JwtSecretKeyConfiguration(JwtConfiguration jwtConfiguration) {
        this.jwtConfiguration = jwtConfiguration;
    }

    @Bean
    public SecretKey secretKey(){
        return Keys.hmacShaKeyFor(jwtConfiguration.getSecret().getBytes(StandardCharsets.UTF_8));
    }
}
