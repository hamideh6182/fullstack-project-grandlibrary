package com.github.hamideh6182.model;

public record BookRequest(
        String title,
        String author,
        String description,
        int copies,
        String category,
        String img
) {

}
