package com.revworkforce.api_gateway.config;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GatewayFallbackController {

    @RequestMapping("/fallback/user-service")
    public ResponseEntity<Map<String, String>> userServiceFallback() {

        Map<String, String> response = Map.of(
                "service", "user-service",
                "message", "User Service is temporarily unavailable",
                "status", "SERVICE_UNAVAILABLE"
        );

        return ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(response);
    }
}