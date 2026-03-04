package com.wcamprog.apispringdev.infrastructure.web;

import com.wcamprog.apispringdev.domain.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {

    record ApiError(
            Instant timestamp,
            int status,
            String error,
            String message,
            String path
    ) {}

    @ExceptionHandler(NotFoundException.class)
    public Mono<org.springframework.http.ResponseEntity<ApiError>> handleNotFound(
            NotFoundException ex,
            org.springframework.web.server.ServerWebExchange exchange
    ) {
        var status = HttpStatus.NOT_FOUND;
        var body = new ApiError(
                Instant.now(),
                status.value(),
                status.getReasonPhrase(),
                ex.getMessage(),
                exchange.getRequest().getPath().value()
        );
        return Mono.just(org.springframework.http.ResponseEntity.status(status).body(body));
    }

    @ExceptionHandler(org.springframework.web.bind.support.WebExchangeBindException.class)
    public Mono<org.springframework.http.ResponseEntity<ApiError>> handleValidation(
            org.springframework.web.bind.support.WebExchangeBindException ex,
            org.springframework.web.server.ServerWebExchange exchange
    ) {
        var status = HttpStatus.BAD_REQUEST;
        String msg = ex.getFieldErrors().isEmpty()
                ? "Validación fallida"
                : ex.getFieldErrors().get(0).getField() + ": " + ex.getFieldErrors().get(0).getDefaultMessage();

        var body = new ApiError(
                Instant.now(),
                status.value(),
                status.getReasonPhrase(),
                msg,
                exchange.getRequest().getPath().value()
        );
        return Mono.just(org.springframework.http.ResponseEntity.status(status).body(body));
    }

    @ExceptionHandler(Exception.class)
    public Mono<org.springframework.http.ResponseEntity<ApiError>> handleGeneric(
            Exception ex,
            org.springframework.web.server.ServerWebExchange exchange
    ) {
        var status = HttpStatus.INTERNAL_SERVER_ERROR;
        var body = new ApiError(
                Instant.now(),
                status.value(),
                status.getReasonPhrase(),
                ex.getMessage(),
                exchange.getRequest().getPath().value()
        );
        return Mono.just(org.springframework.http.ResponseEntity.status(status).body(body));
    }
}