package com.softwaremind.bookstore.model.dto;

public record UpdateBookDTO(
        long id,
        String title,
        String isbn,
        long authorId
) {
}
