package com.pichincha.infrastructure.input.adapter.dto;

import com.pichincha.domain.enums.TipoCuenta;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CuentaDTOTest {

    private Validator validator;
    private CuentaDTO cuentaDTO;

    @BeforeEach
    void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
        cuentaDTO = new CuentaDTO();
    }

    @Test
    void deberiaTenerValidacionExitosaCuandoTodosLosCamposSonValidos() {
        cuentaDTO.setId(UUID.randomUUID());
        cuentaDTO.setNumeroCuenta("1234567890");
        cuentaDTO.setTipoCuenta(TipoCuenta.AHORROS);
        cuentaDTO.setSaldoInicial(new BigDecimal("1000.00"));
        cuentaDTO.setEstado(true);
        cuentaDTO.setClienteIdentificacion("1234567890");

        Set<ConstraintViolation<CuentaDTO>> violations = validator.validate(cuentaDTO);

        assertTrue(violations.isEmpty());
    }

    @Test
    void deberiaFallarValidacionCuandoNumeroCuentaEsInvalido() {
        cuentaDTO.setNumeroCuenta("123");
        cuentaDTO.setTipoCuenta(TipoCuenta.CORRIENTE);
        cuentaDTO.setSaldoInicial(new BigDecimal("500.00"));
        cuentaDTO.setEstado(true);
        cuentaDTO.setClienteIdentificacion("0987654321");

        Set<ConstraintViolation<CuentaDTO>> violations = validator.validate(cuentaDTO);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertTrue(violations.iterator().next().getMessage()
                .contains("El número de cuenta debe tener entre 9 y 10 dígitos"));
    }

    @Test
    void deberiaFallarValidacionCuandoSaldoInicialEsNegativo() {
        cuentaDTO.setNumeroCuenta("9876543210");
        cuentaDTO.setTipoCuenta(TipoCuenta.AHORROS);
        cuentaDTO.setSaldoInicial(new BigDecimal("-100.00"));
        cuentaDTO.setEstado(true);
        cuentaDTO.setClienteIdentificacion("1122334455");

        Set<ConstraintViolation<CuentaDTO>> violations = validator.validate(cuentaDTO);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertTrue(violations.iterator().next().getMessage()
                .contains("El saldo inicial debe ser mayor o igual a 0"));
    }
}
