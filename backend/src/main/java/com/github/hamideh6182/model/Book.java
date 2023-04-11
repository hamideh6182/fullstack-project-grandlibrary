package com.github.hamideh6182.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("books")
public record Book(
        @Id
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
