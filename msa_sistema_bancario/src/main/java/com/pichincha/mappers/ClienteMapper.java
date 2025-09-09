package com.pichincha.mappers;

import com.pichincha.domain.Cliente;
import com.pichincha.dto.ClienteDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ClienteMapper {
    ClienteMapper INSTANCE = Mappers.getMapper(ClienteMapper.class);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "contrasena", source = "contrasena")
    @Mapping(target = "estado", source = "estado")
    @Mapping(target = "nombre", source = "nombre")
    @Mapping(target = "genero", source = "genero")
    @Mapping(target = "edad", source = "edad")
    @Mapping(target = "identificacion", source = "identificacion")
    @Mapping(target = "direccion", source = "direccion")
    @Mapping(target = "telefono", source = "telefono")
    ClienteDTO toDto(Cliente entity);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "contrasena", source = "contrasena")
    @Mapping(target = "estado", source = "estado")
    @Mapping(target = "nombre", source = "nombre")
    @Mapping(target = "genero", source = "genero")
    @Mapping(target = "edad", source = "edad")
    @Mapping(target = "identificacion", source = "identificacion")
    @Mapping(target = "direccion", source = "direccion")
    @Mapping(target = "telefono", source = "telefono")
    Cliente toEntity(ClienteDTO dto);
}
