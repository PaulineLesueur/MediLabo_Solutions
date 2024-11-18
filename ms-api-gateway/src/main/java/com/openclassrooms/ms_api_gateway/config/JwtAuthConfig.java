package com.openclassrooms.ms_api_gateway.config;

import com.openclassrooms.ms_api_gateway.auth.JwtAuthenticationProvider;
import com.openclassrooms.ms_api_gateway.auth.JwtTokenUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
public class JwtAuthConfig {
    @Bean
    public JwtAuthenticationProvider jwtAuthenticationProvider(JwtTokenUtil jwtTokenUtil, UserDetailsService userDetailsService) {
        return new JwtAuthenticationProvider(jwtTokenUtil, userDetailsService);
    }
}
