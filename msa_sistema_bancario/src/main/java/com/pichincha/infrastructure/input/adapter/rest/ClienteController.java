package com.pichincha.infrastructure.input.adapter.rest;

import com.pichincha.application.input.port.ClienteInputPort;
import com.pichincha.domain.Cliente;
import com.pichincha.infrastructure.input.adapter.dto.ClienteDTO;
import com.pichincha.infrastructure.output.adapter.mapper.ClienteMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/clientes")
@CrossOrigin(origins = {"http://localhost:4200", "http://127.0.0.1:4200"})
public class ClienteController {
    private final ClienteInputPort clienteUseCase;
    private final ClienteMapper clienteMapper;

    public ClienteController(ClienteInputPort clienteUseCase, ClienteMapper clienteMapper) {
        this.clienteUseCase = clienteUseCase;
        this.clienteMapper = clienteMapper;
    }

    @GetMapping
    public List<ClienteDTO> getAll() {
        return clienteUseCase.obtenerTodos().stream().map(clienteMapper::toDto).toList();
    }

    @PostMapping
    public ResponseEntity<ClienteDTO> create(@Valid @RequestBody ClienteDTO clienteDTO) {
        Cliente cliente = clienteMapper.toEntity(clienteDTO);
        return ResponseEntity.ok(clienteMapper.toDto(clienteUseCase.crear(cliente)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(clienteMapper.toDto(clienteUseCase.obtenerPorId(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteDTO> update(@PathVariable UUID id, @Valid @RequestBody ClienteDTO clienteDTO) {
        clienteDTO.setId(id);
        Cliente cliente = clienteMapper.toEntity(clienteDTO);
        return ResponseEntity.ok(clienteMapper.toDto(clienteUseCase.actualizar(cliente)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        clienteUseCase.eliminarPorId(id);
        return ResponseEntity.ok().build();
    }
}
