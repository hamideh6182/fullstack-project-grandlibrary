package com.github.hamideh6182.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class GlobalExceptionHandlerTest {
    private GlobalExceptionHandler globalExceptionHandler;

    @BeforeEach
    void setUp() {
        globalExceptionHandler = new GlobalExceptionHandler();
    }

    @Test
    void testHandleBookNotFoundException() {
        // Setup
        BookNotFoundException exception = new BookNotFoundException("Book not found");
        // Execute
        ResponseEntity<Map<String, Object>> response = globalExceptionHandler.handleBookNotFoundException(exception);
        // Verify
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Book not found", response.getBody().get("message"));
        assertNotNull(response.getBody().get("timestamp"));
    }

    @Test
    void testHandleGeneralException() {
        // Execute
        ResponseEntity<Map<String, Object>> response = globalExceptionHandler.handleGeneralException();
        // Verify
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Sorry, Bad request", response.getBody().get("message"));
        assertNotNull(response.getBody().get("timestamp"));
    }
}

