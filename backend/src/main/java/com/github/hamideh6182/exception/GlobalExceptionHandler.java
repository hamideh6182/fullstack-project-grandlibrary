package com.github.hamideh6182.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final String TIMESTAMP = "timestamp";
    private static final String MESSAGE = "message";

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleTaskNotFoundException(BookNotFoundException exception) {
        Map<String, Object> responseBody = new LinkedHashMap<>();
        responseBody.put(TIMESTAMP, Instant.now());
        responseBody.put(MESSAGE, exception.getMessage());
        return new ResponseEntity<>(responseBody, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneralException() {
        Map<String, Object> responseBody = new LinkedHashMap<>();
        responseBody.put(TIMESTAMP, Instant.now());
        responseBody.put(MESSAGE, "Sorry, Bad request");
        return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
    }
}
