package com.pichincha.domain.repository;

import com.pichincha.domain.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.hibernate.annotations.Where;
import java.util.Optional;
import java.util.UUID;

@Where(clause = "estado = true")
public interface ClienteRepository extends JpaRepository<Cliente, UUID> {
    Optional<Cliente> findByClienteId(String clienteId);
    Optional<Cliente> findByIdentificacion(String identificacion);

    @Modifying
    @Query("UPDATE Cliente c SET c.estado = false WHERE c.id = :id")
    void logicalDeleteById(@Param("id") UUID id);
}
