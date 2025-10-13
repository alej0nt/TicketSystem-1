package com.leoalelui.ticketsystem.domain.exception;

import java.time.format.DateTimeParseException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
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

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<ErrorMessage> handleResourceAlreadyExistsException(ResourceAlreadyExistsException e) {
        ErrorMessage errorMessage = new ErrorMessage(e.getMessage());
        return new ResponseEntity<>(errorMessage, HttpStatus.CONFLICT);
    }

    // Para validar fechas que vienen en las query params de un controlador endpoint
    @ExceptionHandler(DateTimeParseException.class)
    public ResponseEntity<ErrorMessage> handleDateTimeParseException(DateTimeParseException ex) {
        ErrorMessage errorMessage = new ErrorMessage("Formato de fecha inválido. Usa el formato yyyy-MM-dd.");
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    // Para validar enums inválidos en path variables
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorMessage> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        String parameterName = ex.getName();
        String invalidValue = ex.getValue() != null ? String.valueOf(ex.getValue()) : "null";
        Class<?> requiredType = ex.getRequiredType();
        
        String errorMsg;
        if (requiredType != null && requiredType.isEnum()) {
            Object[] enumConstants = requiredType.getEnumConstants();
            StringBuilder validValues = new StringBuilder();
            for (int i = 0; i < enumConstants.length; i++) {
                validValues.append(enumConstants[i]);
                if (i < enumConstants.length - 1) {
                    validValues.append(", ");
                }
            }
            errorMsg = String.format("Valor inválido '%s' para el parámetro '%s'. Valores válidos: %s", 
                    invalidValue, parameterName, validValues.toString());
        } else {
            errorMsg = String.format("Valor inválido '%s' para el parámetro '%s'", invalidValue, parameterName);
        }
        
        ErrorMessage errorMessage = new ErrorMessage(errorMsg);
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }
}
