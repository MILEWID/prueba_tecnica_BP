package com.pichincha.domain.enums;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.pichincha.infrastructure.config.converters.TipoCuentaDeserializer;
import com.pichincha.infrastructure.config.converters.TipoCuentaSerializer;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@JsonSerialize(using = TipoCuentaSerializer.class)
@JsonDeserialize(using = TipoCuentaDeserializer.class)
public enum TipoCuenta {
    AHORROS("Ahorros"),
    CORRIENTE("Corriente");

    private final String descripcion;

    @Override
    public String toString() {
        return descripcion;
    }

    public static TipoCuenta fromString(String text) {
        for (TipoCuenta tipo : TipoCuenta.values()) {
            if (tipo.descripcion.equalsIgnoreCase(text) || tipo.name().equalsIgnoreCase(text)) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("No se encontró el tipo de cuenta: " + text);
    }
}
