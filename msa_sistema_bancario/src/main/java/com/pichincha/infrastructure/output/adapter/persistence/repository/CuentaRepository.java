package com.pichincha.infrastructure.output.adapter.persistence.repository;

import com.pichincha.domain.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CuentaRepository extends JpaRepository<Cuenta, UUID> {
    
    @Query("SELECT c FROM Cuenta c WHERE c.estado = true")
    List<Cuenta> findAll();
    
    Optional<Cuenta> findByNumeroCuenta(String numeroCuenta);
    List<Cuenta> findByCliente_Identificacion(String identificacion);

    @Modifying
    @Query("UPDATE Cuenta c SET c.estado = false WHERE c.id = :id")
    void logicalDeleteById(@Param("id") UUID id);
}
