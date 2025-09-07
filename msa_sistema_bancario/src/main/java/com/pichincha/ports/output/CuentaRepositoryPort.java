package com.pichincha.ports.output;

import com.pichincha.domain.Cuenta;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CuentaRepositoryPort {
    List<Cuenta> findAll();
    Optional<Cuenta> findById(UUID id);
    Optional<Cuenta> findByNumeroCuenta(String numeroCuenta);
    List<Cuenta> findByClienteIdentificacion(String identificacion);
    Cuenta save(Cuenta cuenta);
    void deleteById(UUID id);
}
