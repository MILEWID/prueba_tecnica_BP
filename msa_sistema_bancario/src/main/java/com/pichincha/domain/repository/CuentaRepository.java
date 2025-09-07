package com.pichincha.domain.repository;

import com.pichincha.domain.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.hibernate.annotations.Where;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Where(clause = "estado = true")
public interface CuentaRepository extends JpaRepository<Cuenta, UUID> {
    Optional<Cuenta> findByNumeroCuenta(String numeroCuenta);
    List<Cuenta> findByCliente_Identificacion(String identificacion);

    @Modifying
    @Query("UPDATE Cuenta c SET c.estado = false WHERE c.id = :id")
    void logicalDeleteById(@Param("id") UUID id);
}
