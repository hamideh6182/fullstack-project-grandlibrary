package com.github.hamideh6182.exception;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class UnauthorizedExceptionTest {
    @Test
    void testUnauthorizedExceptionWithDefaultMessage() {
        UnauthorizedException exception = Assertions.assertThrows(UnauthorizedException.class,
                () -> {
                    throw new UnauthorizedException();
                });

        Assertions.assertEquals("You are not authorized!", exception.getMessage());
    }

    @Test
    void testUnauthorizedExceptionWithCustomMessage() {
        String message = "You are not authorized to access this Page!";
        UnauthorizedException exception = Assertions.assertThrows(UnauthorizedException.class,
                () -> {
                    throw new UnauthorizedException(message);
                });

        Assertions.assertEquals(message, exception.getMessage());
    }

}