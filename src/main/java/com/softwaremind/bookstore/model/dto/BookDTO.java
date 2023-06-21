package com.softwaremind.bookstore.model.dto;


public record BookDTO (
        long id,
        String title,
        String isbn,
        AuthorDTO author
){}
