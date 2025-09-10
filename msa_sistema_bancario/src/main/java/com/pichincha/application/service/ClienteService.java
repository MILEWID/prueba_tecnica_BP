package com.pichincha.application.service;

import com.pichincha.domain.Cliente;
import com.pichincha.application.input.port.ClienteInputPort;
import com.pichincha.application.output.port.ClienteOutputPort;
import com.pichincha.infrastructure.exception.ClienteNoEncontradoException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;

@Service
public class ClienteService implements ClienteInputPort {
    
    private final ClienteOutputPort clienteRepository;
    
    public ClienteService(ClienteOutputPort clienteRepository) {
        this.clienteRepository = clienteRepository;
    }
    
    @Override
    public List<Cliente> obtenerTodos() {
        return clienteRepository.findAll();
    }
    
    @Override
    public Cliente obtenerPorId(UUID id) {
        return clienteRepository.findById(id)
            .orElseThrow(() -> new ClienteNoEncontradoException(id));
    }
    
    @Override
    public Cliente obtenerPorIdentificacion(String identificacion) {
        return clienteRepository.findByIdentificacion(identificacion)
            .orElseThrow(() -> new ClienteNoEncontradoException(identificacion));
    }
    
    @Override
    public Cliente crear(Cliente cliente) {
        return clienteRepository.save(cliente);
    }
    
    @Override
    public Cliente actualizar(Cliente cliente) {
        obtenerPorId(cliente.getId());
        return clienteRepository.save(cliente);
    }
    
    @Override
    @Transactional
    public void eliminarPorId(UUID id) {
        obtenerPorId(id);
        clienteRepository.deleteById(id);
    }
}
