package com.pichincha.infrastructure.input.rest.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class MovimientoDTO {
    private UUID id;
    
    @NotNull(message = "La fecha es obligatoria")
    private LocalDateTime fecha;
    
    @NotBlank(message = "El tipo de movimiento es obligatorio")
    @Pattern(regexp = "^(CREDITO|DEBITO)$", message = "El tipo de movimiento debe ser CREDITO o DEBITO")
    private String tipoMovimiento;
    
    @NotNull(message = "El valor es obligatorio")
    @DecimalMin(value = "0.01", message = "El valor debe ser mayor a 0")
    @Digits(integer = 10, fraction = 2, message = "El valor debe tener máximo 10 dígitos enteros y 2 decimales")
    private BigDecimal valor;
    
    @NotNull(message = "El saldo inicial es obligatorio")
    @Digits(integer = 10, fraction = 2, message = "El saldo inicial debe tener máximo 10 dígitos enteros y 2 decimales")
    private BigDecimal saldoInicial;
    
    @NotNull(message = "El saldo es obligatorio")
    @Digits(integer = 10, fraction = 2, message = "El saldo debe tener máximo 10 dígitos enteros y 2 decimales")
    private BigDecimal saldo;
    
    @NotNull(message = "El estado es obligatorio")
    private Boolean estado = true;
    
    @NotNull(message = "La cuenta es obligatoria")
    private CuentaDTO cuenta;
}
