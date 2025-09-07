package com.pichincha.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class MovimientoDTO {
    private UUID id;
    private LocalDateTime fecha;
    private String cliente;
    private String numeroCuenta;
    private Double saldoInicial;
    private Boolean estado;
    private Double movimiento;
    private Double saldoDisponible;
}
