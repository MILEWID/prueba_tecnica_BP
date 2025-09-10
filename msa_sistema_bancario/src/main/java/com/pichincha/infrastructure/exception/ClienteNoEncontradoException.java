package com.pichincha.infrastructure.exception;

import java.util.UUID;

public class ClienteNoEncontradoException extends ApiException {
    public ClienteNoEncontradoException(UUID id) {
        super("Cliente no encontrado", "No existe un cliente con el ID: " + id, 404);
    }
    
    public ClienteNoEncontradoException(String identificacion) {
        super("Cliente no encontrado", "No existe un cliente con la identificación: " + identificacion, 404);
    }
}
