package com.pichincha.errors;

public class RelacionInvalidaException extends ApiException {
    public RelacionInvalidaException(String entidad, String campo, Object valor) {
        super("Relación inválida", "No se encontró " + entidad + " con " + campo + ": " + valor, 400);
    }
}
