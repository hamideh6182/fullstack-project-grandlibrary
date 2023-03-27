package com.github.hamideh6182.model;

public record MongoUser(
        String id,
        String username,
        String password,
        String role
) {
}
