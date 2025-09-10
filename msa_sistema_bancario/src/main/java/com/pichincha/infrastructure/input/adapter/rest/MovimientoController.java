package com.pichincha.infrastructure.input.adapter.rest;

import com.pichincha.application.input.port.MovimientoInputPort;
import com.pichincha.domain.Movimiento;
import com.pichincha.infrastructure.input.adapter.dto.MovimientoDTO;
import com.pichincha.infrastructure.output.adapter.mapper.MovimientoMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/movimientos")
@CrossOrigin(origins = {"http://localhost:4200", "http://127.0.0.1:4200"})
public class MovimientoController {
    private final MovimientoInputPort movimientoUseCase;
    private final MovimientoMapper movimientoMapper;

    public MovimientoController(MovimientoInputPort movimientoUseCase, MovimientoMapper movimientoMapper) {
        this.movimientoUseCase = movimientoUseCase;
        this.movimientoMapper = movimientoMapper;
    }

    @GetMapping
    public List<MovimientoDTO> getAll() {
        return movimientoUseCase.obtenerTodos().stream().map(movimientoMapper::toDto).toList();
    }

    @GetMapping("/reporte")
    public ResponseEntity<List<MovimientoDTO>> getReporte(
            @RequestParam String identificacion, 
            @RequestParam String desde, 
            @RequestParam String hasta) {
        LocalDateTime start = LocalDateTime.parse(desde);
        LocalDateTime end = LocalDateTime.parse(hasta);
        List<MovimientoDTO> movimientos = movimientoUseCase.obtenerReporte(identificacion, start, end)
            .stream()
            .map(movimientoMapper::toDto)
            .toList();
        return ResponseEntity.ok(movimientos);
    }

    @GetMapping("/reporte/pdf")
    public ResponseEntity<Map<String, String>> getReportePdf(
            @RequestParam String identificacion, 
            @RequestParam String desde, 
            @RequestParam String hasta) {
        LocalDateTime start = LocalDateTime.parse(desde);
        LocalDateTime end = LocalDateTime.parse(hasta);
        String resultado = movimientoUseCase.generarReportePdf(identificacion, start, end);
        return ResponseEntity.ok(Map.of("resultado", resultado));
    }

    @PostMapping("/operacion")
    public ResponseEntity<Map<String, String>> realizarMovimiento(
            @RequestParam String numeroCuenta, 
            @RequestParam Double movimiento) {
        String resultado = movimientoUseCase.realizarMovimiento(numeroCuenta, movimiento);
        return ResponseEntity.ok(Map.of("resultado", resultado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> eliminarMovimiento(@PathVariable UUID id) {
        movimientoUseCase.eliminarPorId(id);
        return ResponseEntity.ok(Map.of("resultado", "Movimiento eliminado correctamente"));
    }
}
