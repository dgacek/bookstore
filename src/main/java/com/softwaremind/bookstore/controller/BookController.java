package com.softwaremind.bookstore.controller;

import com.softwaremind.bookstore.model.dto.BookDTO;
import com.softwaremind.bookstore.model.mapper.BookMapper;
import com.softwaremind.bookstore.service.BookService;
import lombok.AllArgsConstructor;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class BookController {

    private BookService bookService;
    private final BookMapper bookMapper;

    @GetMapping(path = "/api/v1/books")
    @PageableAsQueryParam
    public Page<BookDTO> getAll(Pageable pageable) {
        return bookService.getAll(pageable).map(bookMapper::toDTO);
    }
}
