package br.com.tokiomarine.seguradora.exception;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> illigalArgumentHandler(IllegalArgumentException e) {
        HttpStatus statusCode = HttpStatus.BAD_REQUEST;
        return ResponseEntity
            .status(statusCode)
            .body(createJsonResBody(statusCode.value(), e.getMessage()));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> entityNotFoundHandler(EntityNotFoundException e) {
        HttpStatus statusCode = HttpStatus.BAD_REQUEST;
        return ResponseEntity
            .status(statusCode)
            .body(createJsonResBody(statusCode.value(), e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        HttpStatus statusCode = HttpStatus.BAD_REQUEST;
        List<String> messages = getArgumentNotValidMessages(ex.getBindingResult().getAllErrors());
        
        return ResponseEntity
            .status(statusCode)
            .body(createJsonResBody(statusCode.value(), messages));
    }

    private List<String> getArgumentNotValidMessages(List<ObjectError> errors) {
        return errors
            .stream()
            .filter(error -> error instanceof FieldError)
            .map(error -> ((FieldError) error).getDefaultMessage())
            .collect(Collectors.toList());
    }

    private Map<String, Object> createJsonResBody(int statusCode, Object message) {
        Map<String, Object> map = new HashMap<>();
        map.put("statusCode", statusCode);
        map.put("message", message);
        return map;
    }

}
