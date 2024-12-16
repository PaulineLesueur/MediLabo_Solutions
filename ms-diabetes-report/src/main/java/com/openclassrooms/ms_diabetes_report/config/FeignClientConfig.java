package com.openclassrooms.ms_diabetes_report.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

@Configuration
public class FeignClientConfig {

    @Bean
    public RequestInterceptor requestInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate template) {
                String token = getCurrentToken();
                 if(token != null && !token.isEmpty()) {
                     template.header("Authorization", "Bearer " + token);
                 }
            }

            private String getCurrentToken() {
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                if(authentication != null && authentication instanceof JwtAuthenticationToken) {
                    Jwt jwt = (Jwt) authentication.getCredentials();
                    return jwt.getTokenValue();
                }
                return null;
            }
        };
    }
}
