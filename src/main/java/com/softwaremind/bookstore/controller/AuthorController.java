package com.softwaremind.bookstore.controller;

import com.softwaremind.bookstore.model.dto.AuthorDTO;
import com.softwaremind.bookstore.service.AuthorService;
import lombok.AllArgsConstructor;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/authors")
@AllArgsConstructor
public class AuthorController {

    private AuthorService authorService;

    @GetMapping
    @PageableAsQueryParam
    public Page<AuthorDTO> getAll(Pageable pageable) {
        return authorService.getAll(pageable);
    }
}
