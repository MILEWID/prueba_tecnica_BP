package com.pichincha.errors;

public class ApiException extends RuntimeException {
    private final String details;
    private final String message;
    private final int codigo;

    public ApiException(String message, String details, int codigo) {
        super(message);
        this.message = message;
        this.details = details;
        this.codigo = codigo;
    }

    public String getDetails() {
        return details;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public int getCodigo() {
        return codigo;
    }
}
