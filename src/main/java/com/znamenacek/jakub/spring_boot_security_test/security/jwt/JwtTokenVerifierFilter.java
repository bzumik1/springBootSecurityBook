package com.znamenacek.jakub.spring_boot_security_test.security.jwt;

import io.jsonwebtoken.Claims;
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
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class JwtTokenVerifierFilter extends OncePerRequestFilter {
    private final JwtConfiguration jwtConfiguration;
    private final SecretKey jwtSecret;

    public JwtTokenVerifierFilter(JwtConfiguration jwtConfiguration, SecretKey jwtSecret) {
        this.jwtConfiguration = jwtConfiguration;
        this.jwtSecret = jwtSecret;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader(jwtConfiguration.getAuthorizationHeader());
        if(header==null || !header.startsWith(jwtConfiguration.getTokenPrefix())){
            filterChain.doFilter(request,response);
            return;
        }

        String jws = header.replace(jwtConfiguration.getTokenPrefix(),"");
        Claims body = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(jws)
                .getBody();

        String user = body.getSubject();
        Set<GrantedAuthority> authorities = ((List<Map<String,String>>)body.get("authorities")).stream()
                .map(stringStringMap -> new SimpleGrantedAuthority(stringStringMap.get("authority")))
                .collect(Collectors.toSet());

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                body.getSubject(),
                null,
                authorities
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request,response);
    }
}
