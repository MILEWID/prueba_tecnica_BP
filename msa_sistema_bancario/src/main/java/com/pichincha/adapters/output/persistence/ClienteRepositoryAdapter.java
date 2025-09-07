package com.pichincha.adapters.output.persistence;

import com.pichincha.domain.Cliente;
import com.pichincha.domain.repository.ClienteRepository;
import com.pichincha.ports.output.ClienteRepositoryPort;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class ClienteRepositoryAdapter implements ClienteRepositoryPort {
    
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
    public Optional<Cliente> findByClienteId(String clienteId) {
        return clienteRepository.findByClienteId(clienteId);
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
    
    @Override
    public boolean existsByIdentificacion(String identificacion) {
        return clienteRepository.findByIdentificacion(identificacion).isPresent();
    }
}
