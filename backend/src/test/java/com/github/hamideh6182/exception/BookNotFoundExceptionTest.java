package com.github.hamideh6182.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BookNotFoundExceptionTest {
    @Test
    void testConstructorWithDefaultMessage() {
        //GIVEN
        BookNotFoundException exception = new BookNotFoundException();
        //THEN
        assertEquals("Current book not found", exception.getMessage());
    }

    @Test
    void testConstructorWithCustomMessage() {
        //GIVEN
        String customMessage = "This is a custom message";
        BookNotFoundException exception = new BookNotFoundException(customMessage);
        //THEN
        assertEquals(customMessage, exception.getMessage());
    }
}
