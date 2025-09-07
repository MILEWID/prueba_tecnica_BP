package com.pichincha.mappers;

import com.pichincha.domain.Cuenta;
import com.pichincha.domain.Cliente;
import com.pichincha.dto.CuentaDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface CuentaMapper {
    CuentaMapper INSTANCE = Mappers.getMapper(CuentaMapper.class);

    @Mapping(target = "clienteNombre", expression = "java(clienteNombreFromCliente(entity.getCliente()))")
    @Mapping(target = "clienteIdentificacion", expression = "java(clienteIdentificacionFromCliente(entity.getCliente()))")
    @Mapping(target = "numeroCuenta", source = "numeroCuenta")
    @Mapping(target = "tipoCuenta", source = "tipoCuenta")
    @Mapping(target = "saldoInicial", source = "saldoInicial")
    @Mapping(target = "estado", source = "estado")
    @Mapping(target = "id", source = "id")
    CuentaDTO toDto(Cuenta entity);

    @Mapping(target = "cliente", expression = "java(clienteFromIdentificacion(dto.getClienteIdentificacion()))")
    @Mapping(target = "numeroCuenta", source = "numeroCuenta")
    @Mapping(target = "tipoCuenta", source = "tipoCuenta")
    @Mapping(target = "saldoInicial", source = "saldoInicial")
    @Mapping(target = "estado", source = "estado")
    @Mapping(target = "id", source = "id")
    Cuenta toEntity(CuentaDTO dto);

    default String clienteNombreFromCliente(Cliente cliente) {
        return cliente != null ? cliente.getNombre() : null;
    }

    default String clienteIdentificacionFromCliente(Cliente cliente) {
        return cliente != null ? cliente.getIdentificacion() : null;
    }

    default Cliente clienteFromIdentificacion(String identificacion) {
        if (identificacion == null) return null;
        Cliente cliente = new Cliente();
        cliente.setIdentificacion(identificacion);
        return cliente;
    }
}
