package com.pichincha.mappers;

import com.pichincha.domain.Cliente;
import com.pichincha.domain.Cuenta;
import com.pichincha.domain.Movimiento;
import com.pichincha.dto.MovimientoDTO;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-09-07T03:56:26-0500",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.1.jar, environment: Java 17.0.9 (GraalVM Community)"
)
@Component
public class MovimientoMapperImpl implements MovimientoMapper {

    @Override
    public MovimientoDTO toDto(Movimiento entity) {
        if ( entity == null ) {
            return null;
        }

        MovimientoDTO movimientoDTO = new MovimientoDTO();

        movimientoDTO.setId( entity.getId() );
        movimientoDTO.setFecha( entity.getFecha() );
        movimientoDTO.setCliente( entityCuentaClienteNombre( entity ) );
        movimientoDTO.setNumeroCuenta( entityCuentaNumeroCuenta( entity ) );
        movimientoDTO.setSaldoInicial( entity.getSaldoInicial() );
        movimientoDTO.setEstado( entity.getEstado() );
        movimientoDTO.setMovimiento( entity.getValor() );
        movimientoDTO.setSaldoDisponible( entity.getSaldo() );

        return movimientoDTO;
    }

    @Override
    public Movimiento toEntity(MovimientoDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Movimiento movimiento = new Movimiento();

        movimiento.setId( dto.getId() );
        movimiento.setFecha( dto.getFecha() );
        movimiento.setValor( dto.getMovimiento() );
        movimiento.setSaldoInicial( dto.getSaldoInicial() );
        movimiento.setSaldo( dto.getSaldoDisponible() );
        movimiento.setEstado( dto.getEstado() );

        movimiento.setCuenta( cuentaFromNumeroCuenta(dto.getNumeroCuenta()) );

        return movimiento;
    }

    private String entityCuentaClienteNombre(Movimiento movimiento) {
        if ( movimiento == null ) {
            return null;
        }
        Cuenta cuenta = movimiento.getCuenta();
        if ( cuenta == null ) {
            return null;
        }
        Cliente cliente = cuenta.getCliente();
        if ( cliente == null ) {
            return null;
        }
        String nombre = cliente.getNombre();
        if ( nombre == null ) {
            return null;
        }
        return nombre;
    }

    private String entityCuentaNumeroCuenta(Movimiento movimiento) {
        if ( movimiento == null ) {
            return null;
        }
        Cuenta cuenta = movimiento.getCuenta();
        if ( cuenta == null ) {
            return null;
        }
        String numeroCuenta = cuenta.getNumeroCuenta();
        if ( numeroCuenta == null ) {
            return null;
        }
        return numeroCuenta;
    }
}
