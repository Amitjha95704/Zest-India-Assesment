package com.example.productapi.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI productApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Product API")
                        .description("REST API for managing products and items")
                        .version("1.0"));
    }
}