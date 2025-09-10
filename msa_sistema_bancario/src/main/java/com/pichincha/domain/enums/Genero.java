package com.pichincha.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Genero {
    MASCULINO("Masculino"),
    FEMENINO("Femenino"),
    OTRO("Otro");

    private final String codigo;

    @Override
    public String toString() {
        return codigo;
    }

    public static Genero fromString(String text) {
        for (Genero genero : Genero.values()) {
            if (genero.codigo.equalsIgnoreCase(text) || genero.name().equalsIgnoreCase(text)) {
                return genero;
            }
        }
        throw new IllegalArgumentException("No se encontró el género: " + text);
    }
}
