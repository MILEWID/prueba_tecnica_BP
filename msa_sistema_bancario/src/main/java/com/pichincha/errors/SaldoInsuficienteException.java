package com.pichincha.errors;

public class SaldoInsuficienteException extends ApiException {
    public SaldoInsuficienteException(Double saldoActual, Double valorDebito) {
        super("Saldo insuficiente", "Saldo no disponible. Saldo actual: " + saldoActual + ", valor a debitar: " + valorDebito, 400);
    }
    
    public SaldoInsuficienteException(String mensaje) {
        super("Saldo insuficiente", mensaje, 400);
    }
}
