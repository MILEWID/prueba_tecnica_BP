package com.pichincha.infrastructure.output.adapter.mapper;

import com.pichincha.domain.Cuenta;
import com.pichincha.domain.Cliente;
import com.pichincha.infrastructure.input.adapter.dto.CuentaDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ClienteMapper.class})
public interface CuentaMapper {

    @Mapping(target = "clienteIdentificacion", source = "cliente.identificacion")
    CuentaDTO toDto(Cuenta entity);
    
    @Mapping(target = "cliente", expression = "java(clienteFromIdentificacion(dto.getClienteIdentificacion()))")
    Cuenta toEntity(CuentaDTO dto);
    
    default Cliente clienteFromIdentificacion(String identificacion) {
        if (identificacion == null) return null;
        Cliente cliente = new Cliente();
        cliente.setIdentificacion(identificacion);
        return cliente;
    }
}
