package com.literalura.exception;

// Clase personalizada para la excepción de recurso no encontrado
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);  // Llama al constructor de la clase padre (RuntimeException)
    }
}
