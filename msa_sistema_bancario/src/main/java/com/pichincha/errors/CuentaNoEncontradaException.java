package com.pichincha.errors;

import java.util.UUID;

public class CuentaNoEncontradaException extends ApiException {
    public CuentaNoEncontradaException(UUID id) {
        super("Cuenta no encontrada", "No existe una cuenta con el ID: " + id, 404);
    }
    
    public CuentaNoEncontradaException(String numeroCuenta) {
        super("Cuenta no encontrada", "No existe una cuenta con el número: " + numeroCuenta, 404);
    }
}
