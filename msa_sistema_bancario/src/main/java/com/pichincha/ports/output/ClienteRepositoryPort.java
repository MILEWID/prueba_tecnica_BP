package com.pichincha.ports.output;

import com.pichincha.domain.Cliente;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ClienteRepositoryPort {
    List<Cliente> findAll();
    Optional<Cliente> findById(UUID id);
    Optional<Cliente> findByClienteId(String clienteId);
    Optional<Cliente> findByIdentificacion(String identificacion);
    Cliente save(Cliente cliente);
    void deleteById(UUID id);
    boolean existsByIdentificacion(String identificacion);
}
