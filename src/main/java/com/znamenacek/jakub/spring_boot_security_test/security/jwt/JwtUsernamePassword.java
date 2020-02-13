package com.znamenacek.jakub.spring_boot_security_test.security.jwt;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class JwtUsernamePassword {
    private String username;
    private String password;
}
