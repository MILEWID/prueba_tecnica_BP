package com.pichincha.errors;

public class DatosInvalidosException extends ApiException {
    public DatosInvalidosException(String campo, String valor) {
        super("Datos inválidos", "El campo '" + campo + "' tiene un valor inválido: " + valor, 400);
    }
    
    public DatosInvalidosException(String mensaje) {
        super("Datos inválidos", mensaje, 400);
    }
}
