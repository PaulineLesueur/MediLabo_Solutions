package com.openclassrooms.ms_api_gateway.config;

import com.openclassrooms.ms_api_gateway.auth.JwtAuthenticationFilter;
import com.openclassrooms.ms_api_gateway.auth.JwtAuthenticationProvider;
import com.openclassrooms.ms_api_gateway.auth.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import java.util.List;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    private final JwtTokenUtil jwtTokenUtil;

    public SecurityConfig(JwtTokenUtil jwtTokenUtil) {
        this.jwtTokenUtil = jwtTokenUtil;
    }

    private static final Logger log = LoggerFactory.getLogger(SecurityConfig.class);

    @Bean
    public WebFilter logFilter() {
        return (ServerWebExchange exchange, WebFilterChain chain) -> {
            String uri = exchange.getRequest().getURI().toString();
            String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");

            log.info("Request URI: {}", uri);
            log.info("Authorization Header: {}", authHeader);

            return chain.filter(exchange);
        };
    }

    @Bean
    public SecurityWebFilterChain filterChain(ServerHttpSecurity http, JwtAuthenticationProvider jwtAuthenticationProvider) throws Exception {

        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.setAllowCredentials(true);
        corsConfig.applyPermitDefaultValues();
        corsConfig.setAllowedOrigins(List.of("http://localhost:4200", "http://localhost:4200/*"));
        corsConfig.setAllowedMethods(List.of("GET", "POST", "OPTIONS", "DELETE", "PUT"));
        corsConfig.setAllowedHeaders(List.of("*"));
        corsConfig.addExposedHeader("Authorization");


        http
                .cors(cors -> cors.configurationSource(request -> corsConfig))
                .csrf(csrf -> csrf.disable())
                .authorizeExchange(auth -> auth
                        .pathMatchers("/public/**", "/auth/**").permitAll()
                        .pathMatchers("/patients", "/patient/{id}").hasAuthority("ORGANIZER")
                        .pathMatchers("/patient/{id}/update", "/patients/create").hasRole("ORGANIZER")
                        .anyExchange().authenticated()
                )
                .addFilterBefore(new JwtAuthenticationFilter(jwtAuthenticationProvider), SecurityWebFiltersOrder.AUTHENTICATION)
        ;

        return http.build();
    }

}
