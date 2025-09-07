package com.pichincha.application.usecases;

import com.pichincha.domain.Movimiento;
import com.pichincha.domain.Cuenta;
import com.pichincha.ports.input.MovimientoUseCase;
import com.pichincha.ports.output.MovimientoRepositoryPort;
import com.pichincha.ports.output.CuentaRepositoryPort;
import com.pichincha.ports.output.ReporteGeneratorPort;
import com.pichincha.errors.MovimientoNoEncontradoException;
import com.pichincha.errors.CuentaNoEncontradaException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class MovimientoUseCaseImpl implements MovimientoUseCase {
    
    private final MovimientoRepositoryPort movimientoRepository;
    private final CuentaRepositoryPort cuentaRepository;
    private final ReporteGeneratorPort reporteGenerator;
    
    public MovimientoUseCaseImpl(MovimientoRepositoryPort movimientoRepository,
                                CuentaRepositoryPort cuentaRepository,
                                ReporteGeneratorPort reporteGenerator) {
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
        Cuenta cuenta = cuentaRepository.findByNumeroCuenta(numeroCuenta)
            .orElseThrow(() -> new CuentaNoEncontradaException("No existe una cuenta con el número: " + numeroCuenta));

        if (!cuenta.getEstado()) {
            return "Cuenta inactiva";
        }
        
        Double saldoActual = cuenta.getSaldoInicial();
        Double nuevoSaldo;
        String tipoMovimiento;
        
        if (movimiento < 0) {
            tipoMovimiento = "Debito";
            Double valorDebito = Math.abs(movimiento);
            if (saldoActual < valorDebito) {
                return "Saldo no disponible";
            }
            nuevoSaldo = saldoActual + movimiento;
        } else {
            tipoMovimiento = "Credito";
            nuevoSaldo = saldoActual + movimiento;
        }
        
        cuenta.setSaldoInicial(nuevoSaldo);
        cuentaRepository.save(cuenta);
        
        Movimiento nuevoMovimiento = new Movimiento();
        nuevoMovimiento.setCuenta(cuenta);
        nuevoMovimiento.setTipoMovimiento(tipoMovimiento);
        nuevoMovimiento.setValor(movimiento);
        nuevoMovimiento.setSaldoInicial(saldoActual);
        nuevoMovimiento.setSaldo(nuevoSaldo);
        nuevoMovimiento.setFecha(LocalDateTime.now());
        nuevoMovimiento.setEstado(true);
        
        movimientoRepository.save(nuevoMovimiento);
        
        return "Movimiento realizado correctamente";
    }

    @Override
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
