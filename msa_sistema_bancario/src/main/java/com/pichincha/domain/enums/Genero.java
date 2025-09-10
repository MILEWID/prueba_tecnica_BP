package com.pichincha.domain.enums;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.pichincha.infrastructure.config.converters.GeneroDeserializer;
import com.pichincha.infrastructure.config.converters.GeneroSerializer;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@JsonSerialize(using = GeneroSerializer.class)
@JsonDeserialize(using = GeneroDeserializer.class)
public enum Genero {
    MASCULINO("M"),
    FEMENINO("F");

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
