package com.pichincha.application.usecases;

import com.pichincha.domain.Cuenta;
import com.pichincha.domain.Cliente;
import com.pichincha.ports.input.CuentaUseCase;
import com.pichincha.ports.output.CuentaRepositoryPort;
import com.pichincha.ports.output.ClienteRepositoryPort;
import com.pichincha.errors.CuentaNoEncontradaException;
import com.pichincha.errors.ClienteNoEncontradoException;
import com.pichincha.errors.DatosInvalidosException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class CuentaUseCaseImpl implements CuentaUseCase {
    
    private final CuentaRepositoryPort cuentaRepository;
    private final ClienteRepositoryPort clienteRepository;
    
    public CuentaUseCaseImpl(CuentaRepositoryPort cuentaRepository, 
                            ClienteRepositoryPort clienteRepository) {
        this.cuentaRepository = cuentaRepository;
        this.clienteRepository = clienteRepository;
    }
    
    @Override
    public List<Cuenta> obtenerTodas() {
        return cuentaRepository.findAll();
    }
    
    @Override
    public List<Cuenta> obtenerPorClienteIdentificacion(String identificacion) {
        return cuentaRepository.findByClienteIdentificacion(identificacion);
    }
    
    @Override
    public Cuenta obtenerPorId(UUID id) {
        return cuentaRepository.findById(id)
            .orElseThrow(() -> new CuentaNoEncontradaException("No existe una cuenta con el ID: " + id));
    }
    
    @Override
    public Cuenta crear(Cuenta cuenta) {
        if (cuenta.getCliente() == null || cuenta.getCliente().getIdentificacion() == null) {
            throw new DatosInvalidosException("identificacion", "null");
        }
        
        Cliente cliente = clienteRepository.findByIdentificacion(cuenta.getCliente().getIdentificacion())
            .orElseThrow(() -> new ClienteNoEncontradoException("No existe un cliente con la identificación: " + cuenta.getCliente().getIdentificacion()));
        
        cuenta.setCliente(cliente);
        return cuentaRepository.save(cuenta);
    }
    
    @Override
    public Cuenta actualizar(Cuenta cuenta) {
        obtenerPorId(cuenta.getId());
        
        if (cuenta.getCliente() != null && cuenta.getCliente().getIdentificacion() != null) {
            Cliente cliente = clienteRepository.findByIdentificacion(cuenta.getCliente().getIdentificacion())
                .orElseThrow(() -> new ClienteNoEncontradoException("No existe un cliente con la identificación: " + cuenta.getCliente().getIdentificacion()));
            cuenta.setCliente(cliente);
        }
        
        return cuentaRepository.save(cuenta);
    }
    
    @Override
    public void eliminarPorId(UUID id) {
        obtenerPorId(id);
        cuentaRepository.deleteById(id);
    }
}
