package com.pichincha.dto;

import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Size;
import java.util.UUID;

@Data
@Schema(description = "Información del cliente bancario")
public class ClienteDTO {
    @Schema(description = "ID único del cliente", example = "123e4567-e89b-12d3-a456-426614174000")
    private UUID id;
    
    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    @Schema(description = "Contraseña del cliente", example = "password123", required = true)
    private String contrasena;
    
    @NotNull(message = "El estado es obligatorio")
    @Schema(description = "Estado activo del cliente", example = "true", required = true)
    private Boolean estado;
    
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no puede exceder 100 caracteres")
    @Schema(description = "Nombre completo del cliente", example = "Juan Pérez", required = true)
    private String nombre;
    
    @Schema(description = "Género del cliente", example = "Masculino")
    private String genero;
    
    @Min(value = 18, message = "La edad mínima es 18 años")
    @Max(value = 120, message = "La edad máxima es 120 años")
    @Schema(description = "Edad del cliente", example = "30")
    private Integer edad;
    
    @NotBlank(message = "La identificación es obligatoria")
    @Size(min = 8, max = 15, message = "La identificación debe tener entre 8 y 15 caracteres")
    @Schema(description = "Número de identificación", example = "1234567890", required = true)
    private String identificacion;
    
    @Schema(description = "Dirección del cliente", example = "Calle 123 #45-67")
    private String direccion;
    
    @Schema(description = "Número de teléfono", example = "+573001234567")
    private String telefono;
}
