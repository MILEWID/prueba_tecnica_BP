package com.pichincha.domain.repository;

import com.pichincha.domain.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Where(clause = "estado = true")
public interface MovimientoRepository extends JpaRepository<Movimiento, UUID> {
    List<Movimiento> findByCuenta_Cliente_IdentificacionAndFechaBetween(String identificacion, LocalDateTime start, LocalDateTime end);

    @Modifying
    @Query("UPDATE Movimiento m SET m.estado = false WHERE m.id = :id")
    void logicalDeleteById(@Param("id") UUID id);
}
