package com.pichincha.infrastructure.output.adapter.persistence;

import com.pichincha.domain.Cliente;
import com.pichincha.infrastructure.output.adapter.persistence.repository.ClienteRepository;
import com.pichincha.application.output.port.ClienteOutputPort;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class ClienteRepositoryAdapter implements ClienteOutputPort {
    
    private final ClienteRepository clienteRepository;
    
    public ClienteRepositoryAdapter(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }
    
    @Override
    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }
    
    @Override
    public Optional<Cliente> findById(UUID id) {
        return clienteRepository.findById(id);
    }
    
    @Override
    public Optional<Cliente> findByIdentificacion(String identificacion) {
        return clienteRepository.findByIdentificacion(identificacion);
    }
    
    @Override
    public Cliente save(Cliente cliente) {
        return clienteRepository.save(cliente);
    }
    
    @Override
    public void deleteById(UUID id) {
        clienteRepository.logicalDeleteById(id);
    }
}
