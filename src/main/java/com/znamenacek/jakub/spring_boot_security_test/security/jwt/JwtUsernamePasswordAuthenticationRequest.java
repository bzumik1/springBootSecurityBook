package com.znamenacek.jakub.spring_boot_security_test.security.jwt;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter @NoArgsConstructor
public class JwtUsernamePasswordAuthenticationRequest {
    private String username;
    private String password;
}
