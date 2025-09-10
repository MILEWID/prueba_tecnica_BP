package com.pichincha.infrastructure.output.adapter.mapper;

import com.pichincha.domain.Movimiento;
import com.pichincha.infrastructure.input.adapter.dto.MovimientoDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {CuentaMapper.class})
public interface MovimientoMapper {

    MovimientoDTO toDto(Movimiento entity);
    
    Movimiento toEntity(MovimientoDTO dto);
}
