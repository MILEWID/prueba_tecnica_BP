package com.pichincha.dto;

import lombok.Data;
import java.util.UUID;

@Data
public class CuentaDTO {
    private UUID id;
    private String clienteNombre;
    private String clienteIdentificacion;
    private String numeroCuenta;
    private String tipoCuenta;
    private Double saldoInicial;
    private Boolean estado;
}
