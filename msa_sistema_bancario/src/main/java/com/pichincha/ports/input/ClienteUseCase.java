package com.pichincha.ports.input;

import com.pichincha.domain.Cliente;
import java.util.List;
import java.util.UUID;

public interface ClienteUseCase {
    List<Cliente> obtenerTodos();
    Cliente obtenerPorId(UUID id);
    Cliente obtenerPorIdentificacion(String identificacion);
    Cliente crear(Cliente cliente);
    Cliente actualizar(Cliente cliente);
    void eliminarPorId(UUID id);
}
