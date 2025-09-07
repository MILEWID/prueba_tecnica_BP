package com.pichincha.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("MSA Sistema Bancario API")
                        .description("API REST para sistema bancario con arquitectura hexagonal aplicando principios SOLID")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Sistema Bancario Team")
                                .email("support@sistemabancario.com")
                                .url("https://sistemabancario.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://springdoc.org")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8081")
                                .description("Servidor de Desarrollo"),
                        new Server()
                                .url("https://api.sistemabancario.com")
                                .description("Servidor de Producción")
                ));
    }
}
