package com.github.hamideh6182.exception;

public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException() {
        super("Current Book not found");
    }

    public BookNotFoundException(String message) {
        super(message);
    }
}
