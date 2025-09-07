package com.pichincha.mappers;

import com.pichincha.domain.Cuenta;
import com.pichincha.dto.CuentaDTO;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-09-07T03:56:26-0500",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.1.jar, environment: Java 17.0.9 (GraalVM Community)"
)
@Component
public class CuentaMapperImpl implements CuentaMapper {

    @Override
    public CuentaDTO toDto(Cuenta entity) {
        if ( entity == null ) {
            return null;
        }

        CuentaDTO cuentaDTO = new CuentaDTO();

        cuentaDTO.setNumeroCuenta( entity.getNumeroCuenta() );
        cuentaDTO.setTipoCuenta( entity.getTipoCuenta() );
        cuentaDTO.setSaldoInicial( entity.getSaldoInicial() );
        cuentaDTO.setEstado( entity.getEstado() );
        cuentaDTO.setId( entity.getId() );

        cuentaDTO.setClienteNombre( clienteNombreFromCliente(entity.getCliente()) );
        cuentaDTO.setClienteIdentificacion( clienteIdentificacionFromCliente(entity.getCliente()) );

        return cuentaDTO;
    }

    @Override
    public Cuenta toEntity(CuentaDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Cuenta cuenta = new Cuenta();

        cuenta.setNumeroCuenta( dto.getNumeroCuenta() );
        cuenta.setTipoCuenta( dto.getTipoCuenta() );
        cuenta.setSaldoInicial( dto.getSaldoInicial() );
        cuenta.setEstado( dto.getEstado() );
        cuenta.setId( dto.getId() );

        cuenta.setCliente( clienteFromIdentificacion(dto.getClienteIdentificacion()) );

        return cuenta;
    }
}
