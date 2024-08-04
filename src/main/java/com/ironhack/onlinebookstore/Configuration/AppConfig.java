package com.ironhack.onlinebookstore.Configuration;

import com.ironhack.onlinebookstore.security.JwtRequestFilter;
import com.ironhack.onlinebookstore.security.JwtUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
public class AppConfig {

    @Bean
    public JwtUtil jwtUtil() {
        return new JwtUtil();
    }

    @Bean
    public JwtRequestFilter jwtRequestFilter(@Lazy JwtUtil jwtUtil, @Lazy UserDetailsService userDetailsService) {
        return new JwtRequestFilter(jwtUtil, userDetailsService);
    }
}