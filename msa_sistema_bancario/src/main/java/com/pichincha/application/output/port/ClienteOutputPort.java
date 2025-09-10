package com.pichincha.application.output.port;

import com.pichincha.domain.Cliente;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ClienteOutputPort {
    List<Cliente> findAll();
    Optional<Cliente> findById(UUID id);
    Optional<Cliente> findByIdentificacion(String identificacion);
    Cliente save(Cliente cliente);
    void deleteById(UUID id);
}
