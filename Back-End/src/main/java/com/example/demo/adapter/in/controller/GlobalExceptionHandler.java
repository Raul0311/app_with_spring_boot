package com.example.demo.adapter.in.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Manejador global de excepciones para convertir errores de la capa de dominio
 * en respuestas HTTP específicas.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    // Maneja la IllegalStateException lanzada en RoleUsecase
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<String> handleIllegalStateException(IllegalStateException ex) {

        return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
    }

}