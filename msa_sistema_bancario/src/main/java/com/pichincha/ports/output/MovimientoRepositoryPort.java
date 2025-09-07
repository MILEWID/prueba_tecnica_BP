package com.pichincha.ports.output;

import com.pichincha.domain.Movimiento;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MovimientoRepositoryPort {
    List<Movimiento> findAll();
    Optional<Movimiento> findById(UUID id);
    Movimiento save(Movimiento movimiento);
    void deleteById(UUID id);
    List<Movimiento> findByClienteIdentificacionAndFechaBetween(String identificacion, LocalDateTime start, LocalDateTime end);
}
