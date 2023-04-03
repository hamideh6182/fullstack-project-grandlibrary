package com.github.hamideh6182.model;

public record Checkout(
        String userId,
        String checkoutDate,
        String returnDate,
        String BookId
) {
}
