package com.znamenacek.jakub.spring_boot_security_test.security;

import com.znamenacek.jakub.spring_boot_security_test.security.jwt.JwtConfiguration;
import com.znamenacek.jakub.spring_boot_security_test.security.jwt.JwtTokenFilter;
import com.znamenacek.jakub.spring_boot_security_test.security.jwt.JwtUsernamePasswordAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.crypto.SecretKey;

@EnableWebSecurity @EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userService;
    private final SecretKey secretKey;
    private final JwtConfiguration jwtConfiguration;

    @Autowired
    public ApplicationSecurityConfiguration(PasswordEncoder passwordEncoder, UserDetailsService userService, SecretKey secretKey, JwtConfiguration jwtConfiguration) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.secretKey = secretKey;
        this.jwtConfiguration = jwtConfiguration;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .addFilter(new JwtUsernamePasswordAuthenticationFilter(authenticationManager(),secretKey,jwtConfiguration))
                .addFilterAfter(new JwtTokenFilter(jwtConfiguration,secretKey),JwtUsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                    .anyRequest()
                    .authenticated()
                    .and()
                .httpBasic();
    }

    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(userService);
        return provider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .authenticationProvider(daoAuthenticationProvider());
    }
}
