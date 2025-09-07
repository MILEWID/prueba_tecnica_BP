package com.pichincha.mappers;

import com.pichincha.domain.Movimiento;
import com.pichincha.domain.Cuenta;
import com.pichincha.dto.MovimientoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface MovimientoMapper {
    MovimientoMapper INSTANCE = Mappers.getMapper(MovimientoMapper.class);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "fecha", source = "fecha")
    @Mapping(target = "cliente", source = "cuenta.cliente.nombre")
    @Mapping(target = "numeroCuenta", source = "cuenta.numeroCuenta")
    @Mapping(target = "saldoInicial", source = "saldoInicial")
    @Mapping(target = "estado", source = "estado")
    @Mapping(target = "movimiento", source = "valor")
    @Mapping(target = "saldoDisponible", source = "saldo")
    MovimientoDTO toDto(Movimiento entity);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "cuenta", expression = "java(cuentaFromNumeroCuenta(dto.getNumeroCuenta()))")
    @Mapping(target = "fecha", source = "fecha")
    @Mapping(target = "valor", source = "movimiento")
    @Mapping(target = "saldoInicial", source = "saldoInicial")
    @Mapping(target = "saldo", source = "saldoDisponible")
    @Mapping(target = "estado", source = "estado")
    Movimiento toEntity(MovimientoDTO dto);

    default Cuenta cuentaFromNumeroCuenta(String numeroCuenta) {
        if (numeroCuenta == null) return null;
        Cuenta cuenta = new Cuenta();
        cuenta.setNumeroCuenta(numeroCuenta);
        return cuenta;
    }
}
