package com.softwaremind.bookstore.controller;

import com.softwaremind.bookstore.model.dto.AddBookDTO;
import com.softwaremind.bookstore.model.dto.BookDTO;
import com.softwaremind.bookstore.model.dto.UpdateBookDTO;
import com.softwaremind.bookstore.model.mapper.BookMapper;
import com.softwaremind.bookstore.service.BookService;
import lombok.AllArgsConstructor;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@AllArgsConstructor
public class BookController {

    private BookService bookService;

    @GetMapping(path = "/api/v1/books")
    @PageableAsQueryParam
    public Page<BookDTO> getAll(@RequestParam(name = "search") Optional<String> search, Pageable pageable) {
        return bookService.getAll(search, pageable).map(BookMapper.INSTANCE::toDTO);
    }

    @GetMapping(path = "/api/v1/books/{id}")
    public BookDTO getById(@PathVariable(name = "id") long id) {
        return BookMapper.INSTANCE.toDTO(bookService.getById(id));
    }

    @PostMapping(path = "/api/v1/books")
    public BookDTO add(@RequestBody AddBookDTO dto) {
        return BookMapper.INSTANCE.toDTO(bookService.add(dto));
    }

    @PutMapping(path = "/api/v1/books")
    public BookDTO update(@RequestBody UpdateBookDTO dto) {
        return BookMapper.INSTANCE.toDTO(bookService.update(dto));
    }

    @DeleteMapping(path = "/api/v1/books/{id}")
    public void delete(@PathVariable(name = "id") long id) {
        bookService.delete(id);
    }
}
