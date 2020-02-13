package com.znamenacek.jakub.spring_boot_security_test.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class JwtUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final SecretKey secretKey;
    private final JwtConfiguration jwtConfiguration;


    public JwtUsernamePasswordAuthenticationFilter(AuthenticationManager authenticationManager, SecretKey secretKey, JwtConfiguration jwtConfiguration) {
        this.authenticationManager = authenticationManager;
        this.secretKey = secretKey;
        this.jwtConfiguration = jwtConfiguration;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            ObjectReader reader = new ObjectMapper().readerFor(JwtUsernamePassword.class);
            JwtUsernamePassword userFromRequest = reader.readValue(request.getInputStream());

            Authentication userForAuthentication = new UsernamePasswordAuthenticationToken(
                userFromRequest.getUsername(),
                userFromRequest.getPassword()
            );

            Authentication authenticatedUser = authenticationManager.authenticate(userForAuthentication);

            return authenticatedUser;

        }catch (IOException ioe){
            System.out.println("SENT JSON FILE COULD NOT BE MAPPED TO USER"); //JUST FOR TESTING
            throw new RuntimeException("SENT JSON FILE COULD NOT BE MAPPED TO USER"); //JUST FOR TESTING REPLACE WITH OWN EXCEPTION
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        String username = authResult.getName();
        Collection<? extends GrantedAuthority> authorities = authResult.getAuthorities();

        String jwsToken = Jwts.builder()
                .setSubject(authResult.getName())
                .claim("authorities",authorities)
                .setIssuedAt(new Date())
                .setExpiration(Date.from(LocalDate.now().plusDays(jwtConfiguration.getTokenExpirationInDays()).atStartOfDay(ZoneId.systemDefault()).toInstant()))
                .signWith(secretKey)
                .compact();

        response.addHeader(jwtConfiguration.getTokenHeader(),jwtConfiguration.getTokenPrefix()+jwsToken);
    }
}
