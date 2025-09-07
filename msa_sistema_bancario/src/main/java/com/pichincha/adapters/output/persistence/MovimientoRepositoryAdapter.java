package com.pichincha.adapters.output.persistence;

import com.pichincha.domain.Movimiento;
import com.pichincha.domain.repository.MovimientoRepository;
import com.pichincha.ports.output.MovimientoRepositoryPort;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class MovimientoRepositoryAdapter implements MovimientoRepositoryPort {
    
    private final MovimientoRepository movimientoRepository;
    
    public MovimientoRepositoryAdapter(MovimientoRepository movimientoRepository) {
        this.movimientoRepository = movimientoRepository;
    }
    
    @Override
    public List<Movimiento> findAll() {
        return movimientoRepository.findAll();
    }
    
    @Override
    public Optional<Movimiento> findById(UUID id) {
        return movimientoRepository.findById(id);
    }
    
    @Override
    public Movimiento save(Movimiento movimiento) {
        return movimientoRepository.save(movimiento);
    }
    
    @Override
    public List<Movimiento> findByClienteIdentificacionAndFechaBetween(String identificacion, LocalDateTime start, LocalDateTime end) {
        return movimientoRepository.findByCuenta_Cliente_IdentificacionAndFechaBetween(identificacion, start, end);
    }

    @Override
    public void deleteById(UUID id) {
        movimientoRepository.logicalDeleteById(id);
    }
}
