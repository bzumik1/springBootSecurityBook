package com.znamenacek.jakub.spring_boot_security_test;


import com.znamenacek.jakub.spring_boot_security_test.security.jwt.JwtConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;


@EnableConfigurationProperties(JwtConfiguration.class)
@SpringBootApplication
public class SpringBootSecurityBookApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootSecurityBookApplication.class, args);
    }
}
