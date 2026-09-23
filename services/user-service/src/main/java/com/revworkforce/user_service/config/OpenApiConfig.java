package com.revworkforce.user_service.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI userServiceOpenAPI() {

        return new OpenAPI()
                .info(new Info()
                        .title("RevWorkforce User Service API")
                        .version("1.0")
                        .description(
                                "User authentication, profile management and RBAC APIs"
                        ))

                // Tells Swagger that APIs can use JWT Bearer authentication
                .addSecurityItem(
                        new SecurityRequirement()
                                .addList("bearerAuth")
                )

                // Defines the JWT authentication scheme
                .components(new Components()
                        .addSecuritySchemes(
                                "bearerAuth",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                        ));
    }
}