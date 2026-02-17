package com.mycompany.javatodolistapitemplatev1;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @io.swagger.v3.oas.annotations.info.Info(title = "Todo List v1", version = "1.0", description = "Your to-do list v1.", contact = @Contact(name = "Leandro Andrade", email = "eusouleoandrade@email.com")))
public class OpenApiConfig {
    @Bean
    GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("Todo List - V1")
                .packagesToScan("com.mycompany.javatodolistapitemplatev1")
                .build();
    }
}