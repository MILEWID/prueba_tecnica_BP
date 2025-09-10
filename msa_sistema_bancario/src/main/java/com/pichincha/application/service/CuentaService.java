package com.pichincha.application.service;

import com.pichincha.domain.Cuenta;
import com.pichincha.domain.Cliente;
import com.pichincha.application.input.port.CuentaInputPort;
import com.pichincha.application.output.port.CuentaOutputPort;
import com.pichincha.application.output.port.ClienteOutputPort;
import com.pichincha.infrastructure.exception.CuentaNoEncontradaException;
import com.pichincha.infrastructure.exception.ClienteNoEncontradoException;
import com.pichincha.infrastructure.exception.DatosInvalidosException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;

@Service
public class CuentaService implements CuentaInputPort {
    
    private final CuentaOutputPort cuentaRepository;
    private final ClienteOutputPort clienteRepository;
    
    public CuentaService(CuentaOutputPort cuentaRepository, 
                            ClienteOutputPort clienteRepository) {
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
    @Transactional
    public void eliminarPorId(UUID id) {
        obtenerPorId(id);
        cuentaRepository.deleteById(id);
    }
}
