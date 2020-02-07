package com.znamenacek.jakub.spring_boot_security_test;

import com.znamenacek.jakub.spring_boot_security_test.security.authentication.User;
import com.znamenacek.jakub.spring_boot_security_test.security.authentication.UserService;
import com.znamenacek.jakub.spring_boot_security_test.security.jwt.JwtConfiguration;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableConfigurationProperties(JwtConfiguration.class)
@SpringBootApplication
public class SpringBootSecurityBookApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootSecurityBookApplication.class, args);
    }
}
