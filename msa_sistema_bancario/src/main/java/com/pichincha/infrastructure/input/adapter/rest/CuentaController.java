package com.pichincha.infrastructure.input.adapter.rest;

import com.pichincha.application.input.port.CuentaInputPort;
import com.pichincha.domain.Cuenta;
import com.pichincha.infrastructure.input.adapter.dto.CuentaDTO;
import com.pichincha.infrastructure.output.adapter.mapper.CuentaMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/cuentas")
@CrossOrigin(origins = {"http://localhost:4200", "http://127.0.0.1:4200"})
public class CuentaController {
    private final CuentaInputPort cuentaUseCase;
    private final CuentaMapper cuentaMapper;

    public CuentaController(CuentaInputPort cuentaUseCase, CuentaMapper cuentaMapper) {
        this.cuentaUseCase = cuentaUseCase;
        this.cuentaMapper = cuentaMapper;
    }

    @GetMapping
    public List<CuentaDTO> getAll() {
        return cuentaUseCase.obtenerTodas().stream().map(cuentaMapper::toDto).toList();
    }

    @PostMapping
    public ResponseEntity<CuentaDTO> create(@Valid @RequestBody CuentaDTO cuentaDTO) {
        Cuenta cuenta = cuentaMapper.toEntity(cuentaDTO);
        return ResponseEntity.ok(cuentaMapper.toDto(cuentaUseCase.crear(cuenta)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CuentaDTO> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(cuentaMapper.toDto(cuentaUseCase.obtenerPorId(id)));
    }

    @GetMapping("/cliente/{identificacion}")
    public ResponseEntity<List<CuentaDTO>> getByClienteIdentificacion(@PathVariable String identificacion) {
        List<Cuenta> cuentas = cuentaUseCase.obtenerPorClienteIdentificacion(identificacion);
        return ResponseEntity.ok(cuentas.stream().map(cuentaMapper::toDto).toList());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CuentaDTO> update(@PathVariable UUID id, @Valid @RequestBody CuentaDTO cuentaDTO) {
        cuentaDTO.setId(id);
        Cuenta cuenta = cuentaMapper.toEntity(cuentaDTO);
        return ResponseEntity.ok(cuentaMapper.toDto(cuentaUseCase.actualizar(cuenta)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        cuentaUseCase.eliminarPorId(id);
        return ResponseEntity.ok().build();
    }
}
