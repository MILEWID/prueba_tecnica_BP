package com.pichincha.infrastructure.config.converters;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.pichincha.domain.enums.Genero;
import java.io.IOException;

public class GeneroDeserializer extends JsonDeserializer<Genero> {
    
    @Override
    public Genero deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String value = p.getValueAsString();
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        
        try {
            return Genero.fromString(value);
        } catch (IllegalArgumentException e) {
            throw new IOException("Valor inválido para Genero: " + value, e);
        }
    }
}
