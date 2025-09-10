package com.pichincha.infrastructure.config.converters;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.pichincha.domain.enums.TipoCuenta;
import java.io.IOException;

public class TipoCuentaSerializer extends JsonSerializer<TipoCuenta> {
    
    @Override
    public void serialize(TipoCuenta value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (value != null) {
            gen.writeString(value.getDescripcion());
        }
    }
}
