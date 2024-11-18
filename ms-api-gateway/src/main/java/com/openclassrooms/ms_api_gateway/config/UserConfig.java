package com.openclassrooms.ms_api_gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class UserConfig {

    @Bean
    public UserDetailsService users() {
        UserDetails organizer = User.builder()
                .username("Organizer")
                .password(passwordEncoder().encode("demo"))
                .roles("ORGANIZER")
                .build();

        UserDetails practitioner = User.builder()
                .username("Practitioner")
                .password(passwordEncoder().encode("demo"))
                .roles("PRACTITIONER")
                .build();

        return new InMemoryUserDetailsManager(organizer, practitioner);
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}