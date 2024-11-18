package com.openclassrooms.ms_api_gateway.auth;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

public class JwtAuthenticationFilter implements WebFilter {

    private final JwtAuthenticationProvider authenticationProvider;

    public JwtAuthenticationFilter(JwtAuthenticationProvider authenticationProvider){
        this.authenticationProvider = authenticationProvider;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String token = extractTokenFromRequest(exchange);

        if(token != null) {
            Authentication authentication = authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(null, token));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        return chain.filter(exchange);
    }

    private String extractTokenFromRequest(ServerWebExchange exchange) {
        String token = exchange.getRequest().getHeaders().getFirst("Authorization");
        if(token != null && token.startsWith("Bearer ")) {
            return token.substring(7);
        }
        return null;
    }
}
