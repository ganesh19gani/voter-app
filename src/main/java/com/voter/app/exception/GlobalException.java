package com.voter.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import javax.validation.ConstraintViolationException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.*;

@ControllerAdvice
public class GlobalException {

    @ExceptionHandler(value = {ConstraintViolationException.class})
    protected ResponseEntity<Map<String, Object>> handleConstraintViolationException(ConstraintViolationException ex) {
        HashMap<String, Object> response = new HashMap<>();
        List<String> errors = new ArrayList<>();
        ex.getConstraintViolations().forEach(violation -> {
            errors.add(violation.getMessage());
        });
        response.put("errors", errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationException(MethodArgumentNotValidException ex) {
        HashMap<String, Object> response = new HashMap<>();
        List<String> errors = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            errors.add(error.getDefaultMessage());
        });
        response.put("errors", errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }


    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<Map<String, Object>> handleSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException ex) {
        HashMap<String, Object> response = new HashMap<>();
        List<String> errors = new ArrayList<>();
        ex.forEach(error -> {
            errors.add("Email should not be duplicate");
        });
        response.put("errors", errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
