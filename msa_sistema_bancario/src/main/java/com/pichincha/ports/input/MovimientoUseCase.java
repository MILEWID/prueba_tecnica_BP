package com.pichincha.ports.input;

import com.pichincha.domain.Movimiento;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface MovimientoUseCase {
    List<Movimiento> obtenerTodos();
    Movimiento obtenerPorId(UUID id);
    Movimiento crear(Movimiento movimiento);
    String realizarMovimiento(String numeroCuenta, Double movimiento);
    void eliminarPorId(UUID id);
    List<Movimiento> obtenerReporte(String identificacion, LocalDateTime desde, LocalDateTime hasta);
    String generarReportePdf(String identificacion, LocalDateTime desde, LocalDateTime hasta);
}
