package com.pichincha.infrastructure.output.adapter.mapper;

import com.pichincha.domain.Cliente;
import com.pichincha.domain.Cuenta;
import com.pichincha.infrastructure.input.adapter.dto.CuentaDTO;
import java.math.BigDecimal;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-09-10T16:28:37-0500",
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

        cuentaDTO.setClienteIdentificacion( entityClienteIdentificacion( entity ) );
        cuentaDTO.setId( entity.getId() );
        cuentaDTO.setNumeroCuenta( entity.getNumeroCuenta() );
        cuentaDTO.setTipoCuenta( entity.getTipoCuenta() );
        if ( entity.getSaldoInicial() != null ) {
            cuentaDTO.setSaldoInicial( BigDecimal.valueOf( entity.getSaldoInicial() ) );
        }
        cuentaDTO.setEstado( entity.getEstado() );

        return cuentaDTO;
    }

    @Override
    public Cuenta toEntity(CuentaDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Cuenta cuenta = new Cuenta();

        cuenta.setId( dto.getId() );
        cuenta.setNumeroCuenta( dto.getNumeroCuenta() );
        cuenta.setTipoCuenta( dto.getTipoCuenta() );
        if ( dto.getSaldoInicial() != null ) {
            cuenta.setSaldoInicial( dto.getSaldoInicial().doubleValue() );
        }
        cuenta.setEstado( dto.getEstado() );

        cuenta.setCliente( clienteFromIdentificacion(dto.getClienteIdentificacion()) );

        return cuenta;
    }

    private String entityClienteIdentificacion(Cuenta cuenta) {
        if ( cuenta == null ) {
            return null;
        }
        Cliente cliente = cuenta.getCliente();
        if ( cliente == null ) {
            return null;
        }
        String identificacion = cliente.getIdentificacion();
        if ( identificacion == null ) {
            return null;
        }
        return identificacion;
    }
}
