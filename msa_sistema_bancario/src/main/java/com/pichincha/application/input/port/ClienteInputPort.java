package com.pichincha.application.input.port;

import com.pichincha.domain.Cliente;
import java.util.List;
import java.util.UUID;

public interface ClienteInputPort {
    List<Cliente> obtenerTodos();
    Cliente obtenerPorId(UUID id);
    Cliente obtenerPorIdentificacion(String identificacion);
    Cliente crear(Cliente cliente);
    Cliente actualizar(Cliente cliente);
    void eliminarPorId(UUID id);
}
