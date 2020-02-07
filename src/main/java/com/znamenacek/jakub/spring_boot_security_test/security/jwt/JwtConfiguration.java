package com.znamenacek.jakub.spring_boot_security_test.security.jwt;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Setter @Getter @NoArgsConstructor
@ConfigurationProperties("application.jwt")
public class JwtConfiguration {
    private String tokenPrefix;
    private String authorizationHeader;
    private String secretKey;
    private int tokenExpirationAfterDays;

}
