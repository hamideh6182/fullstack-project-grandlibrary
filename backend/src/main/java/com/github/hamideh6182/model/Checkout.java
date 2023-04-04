package com.github.hamideh6182.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("checkout")
public record Checkout(
        @Id
        String id,
        String bookId,
        String checkoutDate,
        String returnDate,
        String userId
) {
}
