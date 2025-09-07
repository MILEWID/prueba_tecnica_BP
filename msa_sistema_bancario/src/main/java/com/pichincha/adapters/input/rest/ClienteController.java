package com.pichincha.adapters.input.rest;

import com.pichincha.ports.input.ClienteUseCase;
import com.pichincha.domain.Cliente;
import com.pichincha.mappers.ClienteMapper;
import com.pichincha.dto.ClienteDTO;
import com.pichincha.errors.ApiException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/clientes")
@Tag(name = "Clientes", description = "Operaciones de gestión de clientes bancarios")
public class ClienteController {
    private final ClienteUseCase clienteUseCase;
    private final ClienteMapper clienteMapper;

    public ClienteController(ClienteUseCase clienteUseCase, ClienteMapper clienteMapper) {
        this.clienteUseCase = clienteUseCase;
        this.clienteMapper = clienteMapper;
    }

    @GetMapping
    @Operation(summary = "Obtener todos los clientes", description = "Recupera la lista completa de clientes registrados")
    @ApiResponse(responseCode = "200", description = "Lista de clientes obtenida exitosamente")
    public List<ClienteDTO> getAll() {
        return clienteUseCase.obtenerTodos().stream().map(clienteMapper::toDto).toList();
    }

    @PostMapping
    @Operation(summary = "Crear nuevo cliente", description = "Registra un nuevo cliente en el sistema")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Cliente creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos o identificación duplicada")
    })
    public ResponseEntity<?> create(@Valid @RequestBody ClienteDTO clienteDTO) {
        try {
            Cliente cliente = clienteMapper.toEntity(clienteDTO);
            return ResponseEntity.ok(clienteMapper.toDto(clienteUseCase.crear(cliente)));
        } catch (ApiException ex) {
            return ResponseEntity.status(ex.getCodigo()).body(Map.of(
                "message", ex.getMessage(),
                "details", ex.getDetails()
            ));
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener cliente por ID", description = "Recupera un cliente específico por su identificador único")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
        @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    public ResponseEntity<?> getById(@Parameter(description = "ID único del cliente") @PathVariable UUID id) {
        try {
            return ResponseEntity.ok(clienteMapper.toDto(clienteUseCase.obtenerPorId(id)));
        } catch (ApiException ex) {
            return ResponseEntity.status(ex.getCodigo()).body(Map.of(
                "message", ex.getMessage(),
                "details", ex.getDetails()
            ));
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar cliente", description = "Actualiza los datos de un cliente existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Cliente actualizado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Cliente no encontrado"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    public ResponseEntity<?> update(@Parameter(description = "ID único del cliente") @PathVariable UUID id, 
                                   @Valid @RequestBody ClienteDTO clienteDTO) {
        try {
            clienteDTO.setId(id);
            Cliente cliente = clienteMapper.toEntity(clienteDTO);
            return ResponseEntity.ok(clienteMapper.toDto(clienteUseCase.actualizar(cliente)));
        } catch (ApiException ex) {
            return ResponseEntity.status(ex.getCodigo()).body(Map.of(
                "message", ex.getMessage(),
                "details", ex.getDetails()
            ));
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar cliente", description = "Elimina un cliente del sistema")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Cliente eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    public ResponseEntity<?> delete(@Parameter(description = "ID único del cliente") @PathVariable UUID id) {
        try {
            clienteUseCase.eliminarPorId(id);
            return ResponseEntity.ok().build();
        } catch (ApiException ex) {
            return ResponseEntity.status(ex.getCodigo()).body(Map.of(
                "message", ex.getMessage(),
                "details", ex.getDetails()
            ));
        }
    }
}
