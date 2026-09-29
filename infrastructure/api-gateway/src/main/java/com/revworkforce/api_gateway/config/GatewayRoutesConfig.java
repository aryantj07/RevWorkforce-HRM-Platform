package com.revworkforce.api_gateway.config;

import java.net.URI;
import java.time.Duration;
import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import static org.springframework.cloud.gateway.server.mvc.filter.CircuitBreakerFilterFunctions.circuitBreaker;
import static org.springframework.cloud.gateway.server.mvc.filter.LoadBalancerFilterFunctions.lb;
import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;
import static org.springframework.web.servlet.function.RequestPredicates.path;
import static org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions.uri;
import static org.springframework.cloud.gateway.server.mvc.filter.RetryFilterFunctions.retry;
import static org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions.stripPrefix;

@Configuration
public class GatewayRoutesConfig {

    @Bean
    public RouterFunction<ServerResponse> userServiceRoute() {

        return route("user-service-route")
                .route(path("/api/users/**"), http())
                .before(uri("http://user-service"))
                .filter(retry(config -> config
                        .setRetries(2)
                        .setSeries(Set.of(HttpStatus.Series.SERVER_ERROR))
                        .setMethods(Set.of(HttpMethod.GET))
                        .setBackoff(Duration.ofMillis(100), Duration.ofMillis(500), 2)
                ))
                .filter(circuitBreaker(
                        "userServiceCircuitBreaker",
                        URI.create("forward:/fallback/user-service")
                ))
                .filter(lb("user-service"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> employeeManagementServiceRoute() {

        return route("employee-management-service-route")
                .route(path("/employee-management-service/**"), http())
                .before(uri("http://employee-management-service"))
                .before(stripPrefix(1))
                .filter(lb("employee-management-service"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> leaveServiceRoute() {

        return route("leave-service-route")
                .route(path("/leave-service/**"), http())
                .before(uri("http://leave-service"))
                .before(stripPrefix(1))
                .filter(lb("leave-service"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> performanceServiceRoute() {
        return route("performance-service-route")
                .route(path("/performance-service/**"), http())
                .before(uri("http://performance-service"))
                .before(stripPrefix(1))
                .filter(lb("performance-service"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> notificationServiceRoute() {
        return route("notification-service-route")
                .route(path("/notification-service/**"), http())
                .before(uri("http://notification-service"))
                .before(stripPrefix(1))
                .filter(lb("notification-service"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> reportingServiceRoute() {

        return route("reporting-service-route")
                .route(path("/reporting-service/**"), http())
                .before(uri("http://reporting-service"))
                .before(stripPrefix(1))
                .filter(lb("reporting-service"))
                .build();
    }
}