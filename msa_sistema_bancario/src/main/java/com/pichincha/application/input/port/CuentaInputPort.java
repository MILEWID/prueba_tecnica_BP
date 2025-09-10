package com.pichincha.application.input.port;

import com.pichincha.domain.Cuenta;
import java.util.List;
import java.util.UUID;

public interface CuentaInputPort {
    List<Cuenta> obtenerTodas();
    List<Cuenta> obtenerPorClienteIdentificacion(String identificacion);
    Cuenta obtenerPorId(UUID id);
    Cuenta crear(Cuenta cuenta);
    Cuenta actualizar(Cuenta cuenta);
    void eliminarPorId(UUID id);
}
