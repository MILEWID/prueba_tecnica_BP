package com.pichincha.application.usecases;

import com.pichincha.domain.Cliente;
import com.pichincha.ports.input.ClienteUseCase;
import com.pichincha.ports.output.ClienteRepositoryPort;
import com.pichincha.errors.ClienteNoEncontradoException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class ClienteUseCaseImpl implements ClienteUseCase {
    
    private final ClienteRepositoryPort clienteRepository;
    
    public ClienteUseCaseImpl(ClienteRepositoryPort clienteRepository) {
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
    public void eliminarPorId(UUID id) {
        obtenerPorId(id);
        clienteRepository.deleteById(id);
    }
}
