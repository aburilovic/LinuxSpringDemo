package com.aburilovic.springbootsandbox.config.dev;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@Profile("disable-security")  // Only for 'disable-security' profile
public class NoOpSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests().anyRequest().permitAll()  // Allow all requests without authentication
                .and().csrf().disable();  // Optionally disable CSRF for non-production
        return http.build();
    }
}

