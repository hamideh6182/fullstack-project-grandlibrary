package com.github.hamideh6182.model;

public record MongoUserResponse(
        String id,
        String username,
        String role
) {
}
