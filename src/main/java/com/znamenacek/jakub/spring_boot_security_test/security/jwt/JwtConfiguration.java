package com.znamenacek.jakub.spring_boot_security_test.security.jwt;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jwt")
@Getter @Setter @NoArgsConstructor
public class JwtConfiguration {
    private String tokenPrefix;
    private String tokenHeader;
    private String secret;
    private int tokenExpirationInDays;


}
