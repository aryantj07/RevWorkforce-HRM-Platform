package com.revworkforce.leaveservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("RevWorkforce - Leave Service API")
                        .version("1.0.0")
                        .description("Microservice managing employee leave balances, leave applications, approvals, quotas, and holidays.")
                        .contact(new Contact()
                                .name("Branson")
                                .email("branson@revworkforce.com")));
    }
}
