package com.znamenacek.jakub.spring_boot_security_test.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;

public class JwtUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final JwtConfiguration jwtConfiguration;
    private final SecretKey jwtSecret;

    public JwtUsernamePasswordAuthenticationFilter(AuthenticationManager authenticationManager, JwtConfiguration jwtConfiguration, SecretKey jwtSecret) {
        this.authenticationManager = authenticationManager;
        this.jwtConfiguration = jwtConfiguration;
        this.jwtSecret = jwtSecret;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try{
            ObjectReader reader = new ObjectMapper().reader().forType(JwtUsernamePasswordAuthenticationRequest.class);
            JwtUsernamePasswordAuthenticationRequest jwtUsernamePasswordAuthenticationRequest = reader.readValue(request.getInputStream());

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    jwtUsernamePasswordAuthenticationRequest.getUsername(),
                    jwtUsernamePasswordAuthenticationRequest.getPassword()
            );

            Authentication authenticated = authenticationManager.authenticate(authentication);
            return authenticated;
        }catch (IOException ioe){
            throw new RuntimeException(ioe);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        String jws = Jwts.builder()
                .setSubject(authResult.getName())
                .claim("authorities",authResult.getAuthorities())
                .setIssuedAt(new Date())
                .setExpiration(java.sql.Date.valueOf(LocalDate.now().plusDays(1)))
                .signWith(jwtSecret)
                .compact();

        response.addHeader(jwtConfiguration.getAuthorizationHeader(),jwtConfiguration.getTokenPrefix()+jws);

//                chain.doFilter(request,response);
    }
}
