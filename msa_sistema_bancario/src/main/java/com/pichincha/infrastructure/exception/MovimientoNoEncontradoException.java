package com.pichincha.infrastructure.exception;

import java.util.UUID;

public class MovimientoNoEncontradoException extends ApiException {
    public MovimientoNoEncontradoException(UUID id) {
        super("Movimiento no encontrado", "No existe un movimiento con el ID: " + id, 404);
    }
}
