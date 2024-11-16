package com.openclassrooms.ms_api_gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain filterChain(ServerHttpSecurity http) throws Exception {

        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.setAllowCredentials(true);
        corsConfig.applyPermitDefaultValues();
        corsConfig.setAllowedOrigins(List.of("http://localhost:4200", "http://localhost:4200/*"));
        corsConfig.setAllowedMethods(List.of("GET", "POST", "OPTIONS", "DELETE", "PUT"));
        corsConfig.setAllowedHeaders(List.of("*"));

        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(source -> corsConfig))
                .authorizeExchange(auth -> auth
                        .pathMatchers("/public/**").permitAll()
                        .pathMatchers("/patients", "/patient/{id}").hasAnyRole("ORGANIZER", "PRACTITIONER")
                        .pathMatchers("/patient/{id}/update", "/patients/create").hasRole("ORGANIZER")
                        .anyExchange().authenticated()
                );

        return http.build();
    }

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
