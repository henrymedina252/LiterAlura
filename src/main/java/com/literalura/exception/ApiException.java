package com.literalura.exception;

// Excepción personalizada para manejar errores de la API de Gutendex
public class ApiException extends RuntimeException {
    public ApiException(String message) {
        super(message);
    }

    public ApiException(String message, Throwable cause) {
        super(message, cause);
    }
}
