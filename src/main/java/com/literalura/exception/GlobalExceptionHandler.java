package com.literalura.exception;

import com.literalura.exception.ResourceNotFoundException;  // Importación de la excepción personalizada
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // Maneja excepciones generales
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> manejarExcepcion(Exception ex) {
        logger.error("Error ocurrido: ", ex);
        return new ResponseEntity<>("Se ha producido un error: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Maneja excepciones de recurso no encontrado
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> manejarRecursoNoEncontrado(ResourceNotFoundException ex) {
        logger.warn("Recurso no encontrado: " + ex.getMessage());
        return new ResponseEntity<>("Recurso no encontrado: " + ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    // Maneja excepciones de validación
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> manejarValidacion(MethodArgumentNotValidException ex) {
        StringBuilder errorMessages = new StringBuilder();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            errorMessages.append(error.getDefaultMessage()).append(" ");
        });
        return new ResponseEntity<>("Errores de validación: " + errorMessages.toString(), HttpStatus.BAD_REQUEST);
    }
}
