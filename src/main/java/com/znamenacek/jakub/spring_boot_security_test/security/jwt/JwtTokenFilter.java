package com.znamenacek.jakub.spring_boot_security_test.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class JwtTokenFilter  extends OncePerRequestFilter {
    private final JwtConfiguration jwtConfiguration;
    private final SecretKey secretKey;

    public JwtTokenFilter(JwtConfiguration jwtConfiguration, SecretKey secretKey) {
        this.jwtConfiguration = jwtConfiguration;
        this.secretKey = secretKey;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String tokenHeader = request.getHeader(jwtConfiguration.getTokenHeader());

        if(tokenHeader == null || !tokenHeader.startsWith(jwtConfiguration.getTokenPrefix())){
            return;
        }

        try{
            String jwsToken = tokenHeader.replace(jwtConfiguration.getTokenPrefix(),"");

            Claims body = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(jwsToken)
                    .getBody();

            String username = body.getSubject();

            List< Map<String,String> > authoritiesAsString = (List< Map<String,String> >)body.get("authorities");
            Set<GrantedAuthority> authorities = authoritiesAsString.stream()
                    .map(stringStringMap -> new SimpleGrantedAuthority(stringStringMap.get("authority")))
                    .collect(Collectors.toSet());

            Authentication authenticatedUser = new UsernamePasswordAuthenticationToken(
                    username,
                    null,
                    authorities
            );

            SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
        }catch (JwtException jwte){
            System.out.println("TOKEN COULD NOT BE TRUSTED"); //JUST FOR TESTING
        }

        filterChain.doFilter(request,response);
    }
}
