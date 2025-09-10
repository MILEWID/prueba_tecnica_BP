package com.pichincha.infrastructure.output.adapter.mapper;

import com.pichincha.domain.Movimiento;
import com.pichincha.infrastructure.input.adapter.dto.MovimientoDTO;
import java.math.BigDecimal;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-09-10T00:07:01-0500",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.1.jar, environment: Java 17.0.9 (GraalVM Community)"
)
@Component
public class MovimientoMapperImpl implements MovimientoMapper {

    @Autowired
    private CuentaMapper cuentaMapper;

    @Override
    public MovimientoDTO toDto(Movimiento entity) {
        if ( entity == null ) {
            return null;
        }

        MovimientoDTO movimientoDTO = new MovimientoDTO();

        movimientoDTO.setId( entity.getId() );
        movimientoDTO.setFecha( entity.getFecha() );
        movimientoDTO.setTipoMovimiento( entity.getTipoMovimiento() );
        if ( entity.getValor() != null ) {
            movimientoDTO.setValor( BigDecimal.valueOf( entity.getValor() ) );
        }
        if ( entity.getSaldoInicial() != null ) {
            movimientoDTO.setSaldoInicial( BigDecimal.valueOf( entity.getSaldoInicial() ) );
        }
        if ( entity.getSaldo() != null ) {
            movimientoDTO.setSaldo( BigDecimal.valueOf( entity.getSaldo() ) );
        }
        movimientoDTO.setEstado( entity.getEstado() );
        movimientoDTO.setCuenta( cuentaMapper.toDto( entity.getCuenta() ) );

        return movimientoDTO;
    }

    @Override
    public Movimiento toEntity(MovimientoDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Movimiento movimiento = new Movimiento();

        movimiento.setId( dto.getId() );
        movimiento.setCuenta( cuentaMapper.toEntity( dto.getCuenta() ) );
        movimiento.setFecha( dto.getFecha() );
        movimiento.setTipoMovimiento( dto.getTipoMovimiento() );
        if ( dto.getValor() != null ) {
            movimiento.setValor( dto.getValor().doubleValue() );
        }
        if ( dto.getSaldo() != null ) {
            movimiento.setSaldo( dto.getSaldo().doubleValue() );
        }
        if ( dto.getSaldoInicial() != null ) {
            movimiento.setSaldoInicial( dto.getSaldoInicial().doubleValue() );
        }
        movimiento.setEstado( dto.getEstado() );

        return movimiento;
    }
}
