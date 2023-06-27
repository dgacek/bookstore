package com.softwaremind.bookstore.model.dto;

public record AddBookDTO(
        String title,
        String isbn,
        long authorId
) {}
