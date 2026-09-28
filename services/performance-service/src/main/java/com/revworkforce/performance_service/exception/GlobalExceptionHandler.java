package com.revworkforce.performance_service.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.http.HttpStatus;


import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(
            MethodArgumentNotValidException exception) {

        Map<String, String> errors = new HashMap<>();

        exception.getBindingResult()
                .getFieldErrors()
                .forEach(error ->
                        errors.put(error.getField(), error.getDefaultMessage())
                );

        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(ReviewNotReadyException.class)
    public ResponseEntity<Map<String, String>> handleReviewNotReady(
            ReviewNotReadyException exception) {

        Map<String, String> error = new HashMap<>();

        error.put("error", exception.getMessage());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(error);
    }

    @ExceptionHandler(ReviewWorkflowException.class)
    public ResponseEntity<Map<String, String>> handleReviewWorkflow(
            ReviewWorkflowException exception) {

        Map<String, String> error = new HashMap<>();
        error.put("error", exception.getMessage());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(error);
    }
}