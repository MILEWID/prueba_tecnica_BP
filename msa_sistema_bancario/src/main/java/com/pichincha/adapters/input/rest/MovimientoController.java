package com.pichincha.adapters.input.rest;

import com.pichincha.ports.input.MovimientoUseCase;
import com.pichincha.domain.Movimiento;
import com.pichincha.dto.MovimientoDTO;
import com.pichincha.mappers.MovimientoMapper;
import com.pichincha.errors.ApiException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/movimientos")
public class MovimientoController {
    private final MovimientoUseCase movimientoUseCase;
    private final MovimientoMapper movimientoMapper;

    public MovimientoController(MovimientoUseCase movimientoUseCase, MovimientoMapper movimientoMapper) {
        this.movimientoUseCase = movimientoUseCase;
        this.movimientoMapper = movimientoMapper;
    }

    @GetMapping
    public List<MovimientoDTO> getAll() {
        return movimientoUseCase.obtenerTodos().stream().map(movimientoMapper::toDto).toList();
    }

    @GetMapping("/reporte")
    public ResponseEntity<?> getReporte(@RequestParam String identificacion, @RequestParam String desde, @RequestParam String hasta) {
        try {
            LocalDateTime start = LocalDateTime.parse(desde);
            LocalDateTime end = LocalDateTime.parse(hasta);
            List<MovimientoDTO> movimientos = movimientoUseCase.obtenerReporte(identificacion, start, end)
                .stream()
                .map(movimientoMapper::toDto)
                .toList();
            return ResponseEntity.ok(movimientos);
        } catch (ApiException ex) {
            return ResponseEntity.status(ex.getCodigo()).body(Map.of(
                "message", ex.getMessage(),
                "details", ex.getDetails()
            ));
        }
    }

    @GetMapping("/reporte/pdf")
    public ResponseEntity<?> getReportePdf(@RequestParam String identificacion, @RequestParam String desde, @RequestParam String hasta) {
        try {
            LocalDateTime start = LocalDateTime.parse(desde);
            LocalDateTime end = LocalDateTime.parse(hasta);
            String resultado = movimientoUseCase.generarReportePdf(identificacion, start, end);
            return ResponseEntity.ok(Map.of("resultado", resultado));
        } catch (ApiException ex) {
            return ResponseEntity.status(ex.getCodigo()).body(Map.of(
                "message", ex.getMessage(),
                "details", ex.getDetails()
            ));
        }
    }

    @PostMapping("/operacion")
    public ResponseEntity<?> realizarMovimiento(@RequestParam String numeroCuenta, @RequestParam Double movimiento) {
        try {
            String resultado = movimientoUseCase.realizarMovimiento(numeroCuenta, movimiento);
            return ResponseEntity.ok(Map.of("resultado", resultado));
        } catch (ApiException ex) {
            return ResponseEntity.status(ex.getCodigo()).body(Map.of(
                "message", ex.getMessage(),
                "details", ex.getDetails()
            ));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarMovimiento(@PathVariable UUID id) {
        try {
            movimientoUseCase.eliminarPorId(id);
            return ResponseEntity.ok(Map.of("resultado", "Movimiento eliminado correctamente"));
        } catch (ApiException ex) {
            return ResponseEntity.status(ex.getCodigo()).body(Map.of(
                "message", ex.getMessage(),
                "details", ex.getDetails()
            ));
        }
    }
}
