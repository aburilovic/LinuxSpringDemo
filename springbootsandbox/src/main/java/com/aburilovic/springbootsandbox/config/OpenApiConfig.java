package com.aburilovic.springbootsandbox.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Device API")
                        .version("1.0.0")
                        .description("API documentation for Device management system")
                        .contact(new Contact().name("John Doe").email("john.doe@example.com"))
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")))
                .externalDocs(new ExternalDocumentation()
                        .description("Spring Boot Sandbox readme")
                        .url("https://github.com/aburilovic/LinuxSpringDemo"));
    }
}
