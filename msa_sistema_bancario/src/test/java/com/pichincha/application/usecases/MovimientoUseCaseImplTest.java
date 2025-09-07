package com.pichincha.application.usecases;

import com.pichincha.domain.Cuenta;
import com.pichincha.domain.Movimiento;
import com.pichincha.errors.CuentaNoEncontradaException;
import com.pichincha.errors.MovimientoNoEncontradoException;
import com.pichincha.errors.SaldoInsuficienteException;
import com.pichincha.ports.output.CuentaRepositoryPort;
import com.pichincha.ports.output.MovimientoRepositoryPort;
import com.pichincha.ports.output.ReporteGeneratorPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("MovimientoUseCaseImpl Tests - Lógica Bancaria Compleja con SOLID")
class MovimientoUseCaseImplTest {

    @Mock
    private MovimientoRepositoryPort movimientoRepositoryPort;

    @Mock
    private CuentaRepositoryPort cuentaRepositoryPort;

    @Mock
    private ReporteGeneratorPort reporteGeneratorPort;

    @InjectMocks
    private MovimientoUseCaseImpl movimientoUseCase;

    private Movimiento movimientoEjemplo;
    private Cuenta cuentaEjemplo;
    private UUID movimientoId;
    private String numeroCuenta;

    @BeforeEach
    void setUp() {
        movimientoId = UUID.randomUUID();
        numeroCuenta = "1234567890";
        
        
        cuentaEjemplo = new Cuenta();
        cuentaEjemplo.setId(UUID.randomUUID());
        cuentaEjemplo.setNumeroCuenta(numeroCuenta);
        cuentaEjemplo.setTipoCuenta("AHORROS");
        cuentaEjemplo.setSaldoInicial(new BigDecimal("1000.00"));
        cuentaEjemplo.setEstado(true);

        
        movimientoEjemplo = new Movimiento();
        movimientoEjemplo.setId(movimientoId);
        movimientoEjemplo.setCuenta(cuentaEjemplo);
        movimientoEjemplo.setTipoMovimiento("DEBITO");
        movimientoEjemplo.setValor(new BigDecimal("100.00"));
        movimientoEjemplo.setSaldoInicial(new BigDecimal("1000.00"));
        movimientoEjemplo.setSaldo(new BigDecimal("900.00"));
        movimientoEjemplo.setFecha(LocalDateTime.now());
    }

    @Test
    @DisplayName("Debería crear movimiento de débito exitosamente cuando hay saldo suficiente")
    void deberiaCrearMovimientoDebitoExitosamenteCuandoHaySaldoSuficiente() {
        
        when(cuentaRepositoryPort.findByNumeroCuenta(eq(numeroCuenta)))
                .thenReturn(Optional.of(cuentaEjemplo));
        when(movimientoRepositoryPort.save(any(Movimiento.class)))
                .thenReturn(movimientoEjemplo);

        
        Movimiento movimientoCreado = movimientoUseCase.crear(movimientoEjemplo);

        
        assertNotNull(movimientoCreado);
        assertEquals(movimientoEjemplo.getTipoMovimiento(), movimientoCreado.getTipoMovimiento());
        assertEquals(movimientoEjemplo.getValor(), movimientoCreado.getValor());
        assertEquals(new BigDecimal("1000.00"), movimientoCreado.getSaldoInicial());
        
        verify(cuentaRepositoryPort, times(1)).findByNumeroCuenta(eq(numeroCuenta));
        verify(movimientoRepositoryPort, times(1)).save(any(Movimiento.class));
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando cuenta no existe")
    void deberiaLanzarExcepcionCuandoCuentaNoExiste() {
        
        when(cuentaRepositoryPort.findByNumeroCuenta(eq(numeroCuenta)))
                .thenReturn(Optional.empty());

        
        CuentaNoEncontradaException exception = assertThrows(
            CuentaNoEncontradaException.class,
            () -> movimientoUseCase.crear(movimientoEjemplo)
        );

        assertNotNull(exception);
        assertTrue(exception.getMessage().contains("No existe una cuenta con el número"));
        assertTrue(exception.getMessage().contains(numeroCuenta));
        
        verify(cuentaRepositoryPort, times(1)).findByNumeroCuenta(eq(numeroCuenta));
        verify(movimientoRepositoryPort, never()).save(any(Movimiento.class));
    }

    @Test
    @DisplayName("Debería crear movimiento de crédito exitosamente")
    void deberiaCrearMovimientoCreditoExitosamente() {
        
        movimientoEjemplo.setTipoMovimiento("CREDITO");
        movimientoEjemplo.setValor(new BigDecimal("200.00"));
        movimientoEjemplo.setSaldo(new BigDecimal("1200.00"));

        when(cuentaRepositoryPort.findByNumeroCuenta(eq(numeroCuenta)))
                .thenReturn(Optional.of(cuentaEjemplo));
        when(movimientoRepositoryPort.save(any(Movimiento.class)))
                .thenReturn(movimientoEjemplo);

        
        Movimiento movimientoCreado = movimientoUseCase.crear(movimientoEjemplo);

        
        assertNotNull(movimientoCreado);
        assertEquals("CREDITO", movimientoCreado.getTipoMovimiento());
        assertEquals(new BigDecimal("200.00"), movimientoCreado.getValor());
        assertEquals(new BigDecimal("1000.00"), movimientoCreado.getSaldoInicial());
        
        verify(cuentaRepositoryPort, times(1)).findByNumeroCuenta(eq(numeroCuenta));
        verify(movimientoRepositoryPort, times(1)).save(any(Movimiento.class));
    }

    @Test
    @DisplayName("Debería obtener movimiento por ID exitosamente")
    void deberiaObtenerMovimientoPorIdExitosamente() {
        
        when(movimientoRepositoryPort.findById(eq(movimientoId)))
                .thenReturn(Optional.of(movimientoEjemplo));

        
        Movimiento movimientoEncontrado = movimientoUseCase.obtenerPorId(movimientoId);

        
        assertNotNull(movimientoEncontrado);
        assertEquals(movimientoEjemplo.getId(), movimientoEncontrado.getId());
        assertEquals(movimientoEjemplo.getTipoMovimiento(), movimientoEncontrado.getTipoMovimiento());
        
        verify(movimientoRepositoryPort, times(1)).findById(eq(movimientoId));
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando movimiento no existe por ID")
    void deberiaLanzarExcepcionCuandoMovimientoNoExistePorId() {
        
        when(movimientoRepositoryPort.findById(eq(movimientoId)))
                .thenReturn(Optional.empty());

        
        MovimientoNoEncontradoException exception = assertThrows(
            MovimientoNoEncontradoException.class,
            () -> movimientoUseCase.obtenerPorId(movimientoId)
        );

        assertNotNull(exception);
        assertTrue(exception.getMessage().contains("Movimiento no encontrado"));
        
        verify(movimientoRepositoryPort, times(1)).findById(eq(movimientoId));
    }

    @Test
    @DisplayName("Debería validar correctamente la asignación de cuenta al movimiento")
    void deberiaValidarAsignacionCuentaAlMovimiento() {
        
        Movimiento movimientoSinCuenta = new Movimiento();
        movimientoSinCuenta.setTipoMovimiento("CREDITO");
        movimientoSinCuenta.setValor(new BigDecimal("50.00"));
        
        Cuenta cuentaParaAsignar = new Cuenta();
        cuentaParaAsignar.setNumeroCuenta(numeroCuenta);
        movimientoSinCuenta.setCuenta(cuentaParaAsignar);

        when(cuentaRepositoryPort.findByNumeroCuenta(eq(numeroCuenta)))
                .thenReturn(Optional.of(cuentaEjemplo));
        when(movimientoRepositoryPort.save(any(Movimiento.class)))
                .thenAnswer(invocation -> {
                    Movimiento mov = invocation.getArgument(0);
                    assertEquals(cuentaEjemplo, mov.getCuenta());
                    assertEquals(cuentaEjemplo.getSaldoInicial(), mov.getSaldoInicial());
                    return mov;
                });

        
        Movimiento resultado = movimientoUseCase.crear(movimientoSinCuenta);

        
        assertNotNull(resultado);
        assertEquals(cuentaEjemplo, resultado.getCuenta());
        assertEquals(cuentaEjemplo.getSaldoInicial(), resultado.getSaldoInicial());
        
        verify(cuentaRepositoryPort, times(1)).findByNumeroCuenta(eq(numeroCuenta));
        verify(movimientoRepositoryPort, times(1)).save(any(Movimiento.class));
    }
}
