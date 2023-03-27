package com.github.hamideh6182.exception;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException() {
        super("You are not authorized!");
    }

    public UnauthorizedException(String message) {
        super(message);
    }
}
