package com.pichincha.infrastructure.output.adapter.persistence;

import com.pichincha.domain.Cuenta;
import com.pichincha.application.output.port.CuentaOutputPort;
import com.pichincha.infrastructure.output.adapter.persistence.repository.CuentaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class CuentaRepositoryAdapter implements CuentaOutputPort {
    private final CuentaRepository cuentaRepository;

    public CuentaRepositoryAdapter(CuentaRepository cuentaRepository) {
        this.cuentaRepository = cuentaRepository;
    }

    @Override
    public List<Cuenta> findAll() {
        return cuentaRepository.findAll();
    }
    
    @Override
    public Optional<Cuenta> findById(UUID id) {
        return cuentaRepository.findById(id);
    }
    
    @Override
    public Optional<Cuenta> findByNumeroCuenta(String numeroCuenta) {
        return cuentaRepository.findByNumeroCuenta(numeroCuenta);
    }
    
    @Override
    public List<Cuenta> findByClienteIdentificacion(String identificacion) {
        return cuentaRepository.findByCliente_Identificacion(identificacion);
    }
    
    @Override
    public Cuenta save(Cuenta cuenta) {
        return cuentaRepository.save(cuenta);
    }

    @Override
    public void deleteById(UUID id) {
        cuentaRepository.logicalDeleteById(id);
    }
}
