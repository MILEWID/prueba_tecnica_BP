package com.pichincha.infrastructure.exception;

import com.pichincha.infrastructure.input.adapter.dto.ErrorDto;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import jakarta.validation.ConstraintViolationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(GeneralApplicationException.class)
    public ResponseEntity<ErrorDto> handleGeneralApplicationException(
            GeneralApplicationException ex) {
        ErrorDto errorDto =
                new ErrorDto(
                        OffsetDateTime.now(),
                        ex.getStatusCode().value(),
                        ex.getMessage(),
                        ex.getBusinessMessage());
        errorDto.setCode(ex.getStatusCode().toString());
        errorDto.setDetails(List.of());
        return ResponseEntity.status(ex.getStatusCode()).body(errorDto);
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorDto> handleApiException(ApiException ex) {
        ErrorDto errorDto =
                new ErrorDto(
                        OffsetDateTime.now(),
                        ex.getCodigo(),
                        ex.getMessage(),
                        ex.getDetails());
        errorDto.setCode(HttpStatusCode.valueOf(ex.getCodigo()).toString());
        errorDto.setDetails(List.of(ex.getDetails()));
        return ResponseEntity.status(ex.getCodigo()).body(errorDto);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDto> handleValidationException(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.toList());
        
        ErrorDto errorDto =
                new ErrorDto(
                        OffsetDateTime.now(),
                        400,
                        "Validation failed",
                        "Los datos proporcionados no son válidos");
        errorDto.setCode(HttpStatusCode.valueOf(400).toString());
        errorDto.setDetails(errors);
        return ResponseEntity.status(400).body(errorDto);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorDto> handleConstraintViolationException(ConstraintViolationException ex) {
        List<String> errors = ex.getConstraintViolations()
                .stream()
                .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
                .collect(Collectors.toList());
        
        ErrorDto errorDto =
                new ErrorDto(
                        OffsetDateTime.now(),
                        400,
                        "Constraint violation",
                        "Las restricciones de validación no se cumplieron");
        errorDto.setCode(HttpStatusCode.valueOf(400).toString());
        errorDto.setDetails(errors);
        return ResponseEntity.status(400).body(errorDto);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> handleGenericException(Exception ex) {
        ErrorDto errorDto =
                new ErrorDto(
                        OffsetDateTime.now(),
                        500,
                        ex.getLocalizedMessage(),
                        "An unexpected error occurred");
        errorDto.setCode(HttpStatusCode.valueOf(500).toString());
        errorDto.setDetails(List.of());
        return ResponseEntity.status(500).body(errorDto);
    }
}