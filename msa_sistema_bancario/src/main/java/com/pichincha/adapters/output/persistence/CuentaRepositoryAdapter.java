package com.pichincha.adapters.output.persistence;

import com.pichincha.domain.Cuenta;
import com.pichincha.ports.output.CuentaRepositoryPort;
import com.pichincha.domain.repository.CuentaRepository;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class CuentaRepositoryAdapter implements CuentaRepositoryPort {
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
