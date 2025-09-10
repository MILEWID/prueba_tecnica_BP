package com.pichincha.infrastructure.config.converters;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.pichincha.domain.enums.TipoCuenta;
import java.io.IOException;

public class TipoCuentaDeserializer extends JsonDeserializer<TipoCuenta> {
    
    @Override
    public TipoCuenta deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String value = p.getValueAsString();
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        
        try {
            return TipoCuenta.fromString(value);
        } catch (IllegalArgumentException e) {
            throw new IOException("Valor inválido para TipoCuenta: " + value, e);
        }
    }
}
