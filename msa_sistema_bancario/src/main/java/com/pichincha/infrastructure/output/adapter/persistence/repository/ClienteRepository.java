package com.pichincha.infrastructure.output.adapter.persistence.repository;

import com.pichincha.domain.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ClienteRepository extends JpaRepository<Cliente, UUID> {
    
    @Query("SELECT c FROM Cliente c WHERE c.estado = true")
    List<Cliente> findAll();
    
    Optional<Cliente> findByIdentificacion(String identificacion);

    @Modifying
    @Query("UPDATE Cliente c SET c.estado = false WHERE c.id = :id")
    void logicalDeleteById(@Param("id") UUID id);
}
