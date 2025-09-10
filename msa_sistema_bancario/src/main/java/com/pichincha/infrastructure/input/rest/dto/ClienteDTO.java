package com.pichincha.infrastructure.input.rest.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import com.pichincha.domain.enums.Genero;
import java.util.UUID;

@Data
public class ClienteDTO {
    private UUID id;
    
    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String nombre;
    
    @NotNull(message = "El género es obligatorio")
    private Genero genero;
    
    @NotNull(message = "La edad es obligatoria")
    @Min(value = 18, message = "La edad debe ser mayor a 18 años")
    @Max(value = 120, message = "La edad debe ser menor a 120 años")
    private Integer edad;
    
    @NotBlank(message = "La identificación es obligatoria")
    @Pattern(regexp = "^[0-9]{10}$", message = "La identificación debe tener 10 dígitos")
    private String identificacion;
    
    @NotBlank(message = "La dirección es obligatoria")
    @Size(min = 5, max = 200, message = "La dirección debe tener entre 5 y 200 caracteres")
    private String direccion;
    
    @NotBlank(message = "El teléfono es obligatorio")
    @Pattern(regexp = "^[0-9]{9,10}$", message = "El teléfono debe tener entre 9 y 10 dígitos")
    private String telefono;
    
    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 6, max = 20, message = "La contraseña debe tener entre 6 y 20 caracteres")
    private String contrasena;
    
    @NotNull(message = "El estado es obligatorio")
    private Boolean estado = true;
}
