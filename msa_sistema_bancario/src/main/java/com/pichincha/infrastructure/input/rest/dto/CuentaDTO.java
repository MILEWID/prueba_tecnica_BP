package com.pichincha.infrastructure.input.rest.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import com.pichincha.domain.enums.TipoCuenta;
import java.math.BigDecimal;
import java.util.UUID;

@Data
public class CuentaDTO {
    private UUID id;
    
    @NotBlank(message = "El número de cuenta es obligatorio")
    @Pattern(regexp = "^[0-9]{10}$", message = "El número de cuenta debe tener 10 dígitos")
    private String numeroCuenta;
    
    @NotNull(message = "El tipo de cuenta es obligatorio")
    private TipoCuenta tipoCuenta;
    
    @NotNull(message = "El saldo inicial es obligatorio")
    @DecimalMin(value = "0.0", inclusive = true, message = "El saldo inicial debe ser mayor o igual a 0")
    @Digits(integer = 10, fraction = 2, message = "El saldo debe tener máximo 10 dígitos enteros y 2 decimales")
    private BigDecimal saldoInicial;
    
    @NotNull(message = "El estado es obligatorio")
    private Boolean estado = true;
    
    // Campos del cliente como strings en lugar de objeto ClienteDTO
    @NotBlank(message = "La identificación del cliente es obligatoria")
    private String clienteIdentificacion;
    
    private String clienteNombre;
}
