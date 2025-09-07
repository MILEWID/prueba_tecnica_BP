package com.pichincha.errors;

public class IdentificacionDuplicadaException extends ApiException {
    public IdentificacionDuplicadaException(String identificacion) {
        super("Identificación duplicada", "Ya existe un cliente con la identificación: " + identificacion, 409);
    }
}
