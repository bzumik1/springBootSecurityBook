package com.znamenacek.jakub.spring_boot_security_test.security;

import com.znamenacek.jakub.spring_boot_security_test.security.authentication.UserService;
import com.znamenacek.jakub.spring_boot_security_test.security.jwt.JwtConfiguration;
import com.znamenacek.jakub.spring_boot_security_test.security.jwt.JwtTokenVerifierFilter;
import com.znamenacek.jakub.spring_boot_security_test.security.jwt.JwtUsernamePasswordAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.crypto.SecretKey;

@Configuration @EnableWebSecurity @EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final JwtConfiguration jwtConfiguration;
    private final SecretKey secretKey;

    @Autowired
    public ApplicationSecurityConfiguration(PasswordEncoder passwordEncoder, UserService userService, JwtConfiguration jwtConfiguration, SecretKey secretKey) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.jwtConfiguration = jwtConfiguration;

        this.secretKey = secretKey;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //because of sessions
                .and()
                .addFilter(new JwtUsernamePasswordAuthenticationFilter(authenticationManager(),jwtConfiguration,secretKey))
                .addFilterAfter(new JwtTokenVerifierFilter(jwtConfiguration,secretKey),JwtUsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                    .anyRequest()
                    .authenticated()
                    .and()
                .httpBasic();
    }

    @Bean
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
