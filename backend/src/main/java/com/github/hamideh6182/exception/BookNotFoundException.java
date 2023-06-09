package com.github.hamideh6182.exception;

public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException() {
        super("Current book not found");
    }

    public BookNotFoundException(String message) {
        super(message);
    }
}
