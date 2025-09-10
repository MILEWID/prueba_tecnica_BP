package com.pichincha.infrastructure.exception;

import org.springframework.http.HttpStatusCode;

public class GeneralApplicationException extends ApiException {
    private final HttpStatusCode statusCode;
    private final String businessMessage;

    public GeneralApplicationException(String message, String businessMessage, HttpStatusCode statusCode) {
        super(message, businessMessage, statusCode.value());
        this.statusCode = statusCode;
        this.businessMessage = businessMessage;
    }

    public GeneralApplicationException(String message, String businessMessage, int statusCode) {
        super(message, businessMessage, statusCode);
        this.statusCode = HttpStatusCode.valueOf(statusCode);
        this.businessMessage = businessMessage;
    }

    public HttpStatusCode getStatusCode() {
        return statusCode;
    }

    public String getBusinessMessage() {
        return businessMessage;
    }
}
