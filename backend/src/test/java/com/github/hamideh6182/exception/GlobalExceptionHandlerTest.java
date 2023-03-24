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
        // GIVEN
        BookNotFoundException exception = new BookNotFoundException("Book not found");
        ResponseEntity<Map<String, Object>> response = globalExceptionHandler.handleBookNotFoundException(exception);
        Object actual = response.getBody().get("message");
        Object expected = "Book not found";
        // THEN
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(expected, actual);
        assertNotNull(response.getBody().get("timestamp"));
    }

    @Test
    void testHandleGeneralException() {
        // GIVEN
        ResponseEntity<Map<String, Object>> response = globalExceptionHandler.handleGeneralException();
        Object actual = response.getBody().get("message");
        Object expected = "Sorry, Bad request";
        // THEN
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(expected, actual);
        assertNotNull(response.getBody().get("timestamp"));
    }
}

