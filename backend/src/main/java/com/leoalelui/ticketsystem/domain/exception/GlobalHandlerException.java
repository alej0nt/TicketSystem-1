package com.leoalelui.ticketsystem.domain.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalHandlerException {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();

        e.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField(); // nombre del campo
            String errorMessage = error.getDefaultMessage(); // mensaje personalizado
            errors.put(fieldName, errorMessage); // agrega al mapa
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorMessage> handleDataIntegrityViolationException() {
        ErrorMessage errorMessage = new ErrorMessage("Datos de entrada incorrectos.");
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleEntityNotFoundException(ResourceNotFoundException e) {
        ErrorMessage errorMessage = new ErrorMessage(e.getMessage());
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidStateException.class)
    public ResponseEntity<ErrorMessage> handleInvalidStateException(InvalidStateException e) {
        ErrorMessage errorMessage = new ErrorMessage(e.getMessage());
        return new ResponseEntity<>(errorMessage, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidRoleException.class)
    public ResponseEntity<ErrorMessage> handleInvalidRoleException(InvalidRoleException e) {
        ErrorMessage errorMessage = new ErrorMessage(e.getMessage());
        return new ResponseEntity<>(errorMessage, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorMessage> handleBadCredentialsException(BadCredentialsException e) {
        ErrorMessage errorMessage = new ErrorMessage("Credenciales inválidas");
        return new ResponseEntity<>(errorMessage, HttpStatus.UNAUTHORIZED);
    }

}
