package com.pichincha.infrastructure.output.adapter.mapper;

import com.pichincha.domain.Cliente;
import com.pichincha.infrastructure.input.adapter.dto.ClienteDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClienteMapper {
    
    ClienteDTO toDto(Cliente entity);
    Cliente toEntity(ClienteDTO dto);
}
