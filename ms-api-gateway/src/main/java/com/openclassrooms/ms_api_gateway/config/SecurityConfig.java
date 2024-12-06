package com.openclassrooms.ms_api_gateway.config;

import com.nimbusds.jose.jwk.source.ImmutableSecret;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;

import javax.crypto.spec.SecretKeySpec;
import java.util.List;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Value("${security.jwt.secret}")
    private String jwtKey;

    @Bean
    public ReactiveJwtDecoder jwtDecoder() {
        SecretKeySpec secretKey = new SecretKeySpec(this.jwtKey.getBytes(), MacAlgorithm.HS256.getName());
        return NimbusReactiveJwtDecoder.withSecretKey(secretKey).macAlgorithm(MacAlgorithm.HS256).build();
    }



    @Bean
    public JwtEncoder jwtEncoder() {
        return new NimbusJwtEncoder(new ImmutableSecret<>(this.jwtKey.getBytes()));
    }

    @Bean
    public SecurityWebFilterChain filterChain(ServerHttpSecurity http) throws Exception {

        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.setAllowCredentials(true);
        corsConfig.applyPermitDefaultValues();
        corsConfig.setAllowedOrigins(List.of("http://localhost:4200", "http://localhost:4200/*"));
        corsConfig.setAllowedMethods(List.of("GET", "POST", "OPTIONS", "DELETE", "PUT"));
        corsConfig.setAllowedHeaders(List.of("*"));
        corsConfig.addExposedHeader("Authorization");

        http
                //.cors(cors -> cors.configurationSource(request -> corsConfig))
                .csrf(csrf -> csrf.disable())
                .oauth2ResourceServer((oauth2) -> oauth2.jwt(Customizer.withDefaults()))
                .authorizeExchange(auth -> auth
                        .pathMatchers("/public/**").permitAll()
                        .pathMatchers("/patients", "/patient/{id}").hasAnyRole("ORGANIZER", "PRACTITIONER")
                        .pathMatchers("/patient/{id}/update", "/patients/create").hasRole("ORGANIZER")
                        .anyExchange().authenticated()
                )
                .httpBasic();

        return http.build();
    }

    @Bean
    public ReactiveAuthenticationManager authenticationManager(ReactiveUserDetailsService userDetailsService) {
        return new UserDetailsRepositoryReactiveAuthenticationManager(userDetailsService);
    }

    @Bean
    public ReactiveUserDetailsService users() {
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

        return new MapReactiveUserDetailsService(organizer, practitioner);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}
