package org.example.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.Contact
import io.swagger.v3.oas.models.servers.Server
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenApiConfig {
    @Bean
    fun customOpenAPI(): OpenAPI {
        return OpenAPI()
            .info(
                Info()
                    .title("Basic Spring Boot Endpoints for buisnessChat Task")
                    .version("1.0")
                    .description("API Documentation for your Spring Boot application")
                    .contact(
                        Contact()
                            .name("Mohammad Abushaqra")
                            .email("abushaqra4@gmail.com")
                    )
            )
            .addServersItem(Server().url("/").description("Default Server URL"))
    }
}