package com.pichincha.adapters.input.rest;

import com.pichincha.ports.input.CuentaUseCase;
import com.pichincha.domain.Cuenta;
import com.pichincha.dto.CuentaDTO;
import com.pichincha.mappers.CuentaMapper;
import com.pichincha.errors.ApiException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/cuentas")
public class CuentaController {
    private final CuentaUseCase cuentaUseCase;
    private final CuentaMapper cuentaMapper;

    public CuentaController(CuentaUseCase cuentaUseCase, CuentaMapper cuentaMapper) {
        this.cuentaUseCase = cuentaUseCase;
        this.cuentaMapper = cuentaMapper;
    }

    @GetMapping
    public List<CuentaDTO> getAll() {
        return cuentaUseCase.obtenerTodas().stream().map(cuentaMapper::toDto).toList();
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CuentaDTO cuentaDTO) {
        try {
            Cuenta cuenta = cuentaMapper.toEntity(cuentaDTO);
            return ResponseEntity.ok(cuentaMapper.toDto(cuentaUseCase.crear(cuenta)));
        } catch (ApiException ex) {
            return ResponseEntity.status(ex.getCodigo()).body(Map.of(
                "message", ex.getMessage(),
                "details", ex.getDetails()
            ));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        try {
            return ResponseEntity.ok(cuentaMapper.toDto(cuentaUseCase.obtenerPorId(id)));
        } catch (ApiException ex) {
            return ResponseEntity.status(ex.getCodigo()).body(Map.of(
                "message", ex.getMessage(),
                "details", ex.getDetails()
            ));
        }
    }

    @GetMapping("/cliente/{identificacion}")
    public ResponseEntity<?> getByClienteIdentificacion(@PathVariable String identificacion) {
        try {
            List<Cuenta> cuentas = cuentaUseCase.obtenerPorClienteIdentificacion(identificacion);
            return ResponseEntity.ok(cuentas.stream().map(cuentaMapper::toDto).toList());
        } catch (ApiException ex) {
            return ResponseEntity.status(ex.getCodigo()).body(Map.of(
                "message", ex.getMessage(),
                "details", ex.getDetails()
            ));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody CuentaDTO cuentaDTO) {
        try {
            cuentaDTO.setId(id);
            Cuenta cuenta = cuentaMapper.toEntity(cuentaDTO);
            return ResponseEntity.ok(cuentaMapper.toDto(cuentaUseCase.actualizar(cuenta)));
        } catch (ApiException ex) {
            return ResponseEntity.status(ex.getCodigo()).body(Map.of(
                "message", ex.getMessage(),
                "details", ex.getDetails()
            ));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        try {
            cuentaUseCase.eliminarPorId(id);
            return ResponseEntity.ok().build();
        } catch (ApiException ex) {
            return ResponseEntity.status(ex.getCodigo()).body(Map.of(
                "message", ex.getMessage(),
                "details", ex.getDetails()
            ));
        }
    }
}
