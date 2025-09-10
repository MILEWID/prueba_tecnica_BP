package com.pichincha.infrastructure.config.converters;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.pichincha.domain.enums.Genero;
import java.io.IOException;

public class GeneroSerializer extends JsonSerializer<Genero> {
    
    @Override
    public void serialize(Genero value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (value != null) {
            gen.writeString(value.getCodigo());
        }
    }
}
