package com.openclassrooms.ms_api_gateway.auth;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;

import java.util.stream.Collectors;

public class JwtAuthenticationConverter {
    private final JwtDecoder jwtDecoder;

    public JwtAuthenticationConverter(JwtDecoder jwtDecoder) {
        this.jwtDecoder = jwtDecoder;
    }

    public UsernamePasswordAuthenticationToken convert(Jwt jwt) {
        String username = jwt.getSubject();
        var roles = jwt.getClaimAsStringList("roles").stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        return new UsernamePasswordAuthenticationToken(username, jwt.getTokenValue(), roles);
    }
}
