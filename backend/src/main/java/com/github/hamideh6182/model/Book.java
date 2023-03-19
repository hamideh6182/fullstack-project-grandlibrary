package com.github.hamideh6182.model;

public record Book(
        String id,
        String title,
        String author,
        String description,
        int copies,
        int copiesAvailable,
        String category,
        String img
) {
}
