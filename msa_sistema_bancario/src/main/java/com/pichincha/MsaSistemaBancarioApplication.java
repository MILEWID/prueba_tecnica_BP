package com.pichincha;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.Contact;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
    info = @Info(
        title = "Sistema Bancario API",
        version = "1.0.0",
        description = "API REST para gestión bancaria con arquitectura hexagonal. " +
                     "Implementa principios SOLID: Single Responsibility (cada clase tiene una responsabilidad específica), " +
                     "Open/Closed (extendible sin modificar código existente), " +
                     "Liskov Substitution (interfaces intercambiables), " +
                     "Interface Segregation (interfaces específicas), " +
                     "Dependency Inversion (dependencias por abstracción).",
        contact = @Contact(name = "Banco Pichincha", email = "dev@pichincha.com")
    )
)
public class MsaSistemaBancarioApplication {
    public static void main(String[] args) {
        SpringApplication.run(MsaSistemaBancarioApplication.class, args);
    }
}
