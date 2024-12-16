package com.openclassrooms.ms_api_gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("ms-patients", r -> r.path("/patient/**", "/patients/**")
                        .filters(f -> f.preserveHostHeader())
                        .uri("http://ms-patient:8081"))
                .route("ms-notes", r -> r.path("/notes/**")
                        .filters(f -> f.preserveHostHeader())
                        .uri("http://ms-notes:8082"))
                .route("ms-diabetes-report", r -> r.path("/diabetes-report/**")
                        .filters(f -> f.preserveHostHeader())
                        .uri("http://ms-diabetes-report:8083"))
                .build();
    }
}
