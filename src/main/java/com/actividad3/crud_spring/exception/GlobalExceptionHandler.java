package com.actividad3.crud_spring.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> validacion(MethodArgumentNotValidException ex) {
        Map<String, String> errores = new LinkedHashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(e -> errores.putIfAbsent(e.getField(), e.getDefaultMessage()));
        return construir(HttpStatus.BAD_REQUEST, "Datos inválidos", errores);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> jsonInvalido(HttpMessageNotReadableException ex) {
        return construir(HttpStatus.BAD_REQUEST, "El cuerpo de la petición no es un JSON válido", null);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> credenciales(BadCredentialsException ex) {
        return construir(HttpStatus.UNAUTHORIZED, "Correo o contraseña incorrectos", null);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorResponse> estado(ResponseStatusException ex) {
        HttpStatus status = HttpStatus.valueOf(ex.getStatusCode().value());
        return construir(status, ex.getReason(), null);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> integridad(DataIntegrityViolationException ex) {
        return construir(HttpStatus.CONFLICT, "La operación viola una restricción de la base de datos", null);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> general(Exception ex) {
        return construir(HttpStatus.INTERNAL_SERVER_ERROR, "Error interno del servidor", null);
    }

    private ResponseEntity<ErrorResponse> construir(HttpStatus status, String mensaje, Map<String, String> errores) {
        return ResponseEntity.status(status).body(new ErrorResponse(
                Instant.now().toString(), status.value(), status.getReasonPhrase(), mensaje, errores));
    }
}