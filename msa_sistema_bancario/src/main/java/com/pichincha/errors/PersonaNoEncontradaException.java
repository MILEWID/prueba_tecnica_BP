package com.pichincha.errors;

public class PersonaNoEncontradaException extends ApiException {
    public PersonaNoEncontradaException(Long id) {
        super("Persona no encontrada", "No existe una persona con el ID: " + id, 404);
    }
}
