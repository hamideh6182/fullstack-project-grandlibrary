package com.github.hamideh6182.model;

public record MongoUserRequest(
        String username,
        String password
) {
}