package com.pichincha.application.service;

import com.pichincha.domain.Movimiento;
import com.pichincha.domain.Cuenta;
import com.pichincha.application.input.port.MovimientoInputPort;
import com.pichincha.application.output.port.MovimientoOutputPort;
import com.pichincha.application.output.port.CuentaOutputPort;
import com.pichincha.application.output.port.ReporteOutputPort;
import com.pichincha.infrastructure.exception.MovimientoNoEncontradoException;
import com.pichincha.infrastructure.exception.CuentaNoEncontradaException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class MovimientoService implements MovimientoInputPort {
    
    private final MovimientoOutputPort movimientoRepository;
    private final CuentaOutputPort cuentaRepository;
    private final ReporteOutputPort reporteGenerator;
    
    public MovimientoService(MovimientoOutputPort movimientoRepository,
                                CuentaOutputPort cuentaRepository,
                                ReporteOutputPort reporteGenerator) {
        this.movimientoRepository = movimientoRepository;
        this.cuentaRepository = cuentaRepository;
        this.reporteGenerator = reporteGenerator;
    }
    
    @Override
    public List<Movimiento> obtenerTodos() {
        return movimientoRepository.findAll();
    }
    
    @Override
    public Movimiento obtenerPorId(UUID id) {
        return movimientoRepository.findById(id)
            .orElseThrow(() -> new MovimientoNoEncontradoException(id));
    }
    
    @Override
    public Movimiento crear(Movimiento movimiento) {
        if (movimiento.getCuenta() != null && movimiento.getCuenta().getNumeroCuenta() != null) {
            Cuenta cuenta = cuentaRepository.findByNumeroCuenta(movimiento.getCuenta().getNumeroCuenta())
                .orElseThrow(() -> new CuentaNoEncontradaException("No existe una cuenta con el número: " + movimiento.getCuenta().getNumeroCuenta()));
            movimiento.setCuenta(cuenta);
            movimiento.setSaldoInicial(cuenta.getSaldoInicial());
        }
        return movimientoRepository.save(movimiento);
    }
    
    @Override
    @Transactional
    public String realizarMovimiento(String numeroCuenta, Double movimiento) {
        Cuenta cuenta = validarCuenta(numeroCuenta);
        
        if (!cuenta.getEstado()) {
            return "Cuenta inactiva";
        }
        
        String validacionSaldo = validarSaldoDisponible(cuenta, movimiento);
        if (validacionSaldo != null) {
            return validacionSaldo;
        }
        
        Double saldoActual = cuenta.getSaldoInicial();
        String tipoMovimiento = determinarTipoMovimiento(movimiento);
        Double nuevoSaldo = calcularNuevoSaldo(saldoActual, movimiento);
        
        actualizarSaldoCuenta(cuenta, nuevoSaldo);
        crearRegistroMovimiento(cuenta, movimiento, tipoMovimiento, saldoActual, nuevoSaldo);
        
        return "Movimiento realizado correctamente";
    }
    
    private Cuenta validarCuenta(String numeroCuenta) {
        return cuentaRepository.findByNumeroCuenta(numeroCuenta)
            .orElseThrow(() -> new CuentaNoEncontradaException("No existe una cuenta con el número: " + numeroCuenta));
    }
    
    private String validarSaldoDisponible(Cuenta cuenta, Double movimiento) {
        if (movimiento < 0) {
            Double valorDebito = Math.abs(movimiento);
            if (cuenta.getSaldoInicial() < valorDebito) {
                return "Saldo no disponible";
            }
        }
        return null;
    }
    
    private String determinarTipoMovimiento(Double movimiento) {
        return movimiento < 0 ? "Debito" : "Credito";
    }
    
    private Double calcularNuevoSaldo(Double saldoActual, Double movimiento) {
        return saldoActual + movimiento;
    }
    
    private void actualizarSaldoCuenta(Cuenta cuenta, Double nuevoSaldo) {
        cuenta.setSaldoInicial(nuevoSaldo);
        cuentaRepository.save(cuenta);
    }
    
    private void crearRegistroMovimiento(Cuenta cuenta, Double valor, String tipoMovimiento, 
                                       Double saldoInicial, Double nuevoSaldo) {
        Movimiento nuevoMovimiento = new Movimiento();
        nuevoMovimiento.setCuenta(cuenta);
        nuevoMovimiento.setTipoMovimiento(tipoMovimiento);
        nuevoMovimiento.setValor(valor);
        nuevoMovimiento.setSaldoInicial(saldoInicial);
        nuevoMovimiento.setSaldo(nuevoSaldo);
        nuevoMovimiento.setFecha(LocalDateTime.now());
        nuevoMovimiento.setEstado(true);
        
        movimientoRepository.save(nuevoMovimiento);
    }

    @Override
    @Transactional
    public void eliminarPorId(UUID id) {
        obtenerPorId(id);
        movimientoRepository.deleteById(id);
    }
    
    @Override
    public List<Movimiento> obtenerReporte(String identificacion, LocalDateTime desde, LocalDateTime hasta) {
        return movimientoRepository.findByClienteIdentificacionAndFechaBetween(identificacion, desde, hasta);
    }
    
    @Override
    public String generarReportePdf(String identificacion, LocalDateTime desde, LocalDateTime hasta) {
        List<Movimiento> movimientos = obtenerReporte(identificacion, desde, hasta);
        return reporteGenerator.generarReportePdf(movimientos);
    }
}
