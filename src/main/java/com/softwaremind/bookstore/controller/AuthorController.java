package com.softwaremind.bookstore.controller;

import com.softwaremind.bookstore.exception.ObjectAlreadyExistsException;
import com.softwaremind.bookstore.exception.ObjectNotFoundException;
import com.softwaremind.bookstore.model.dto.AddAuthorDTO;
import com.softwaremind.bookstore.model.dto.AuthorDTO;
import com.softwaremind.bookstore.model.mapper.AuthorMapper;
import com.softwaremind.bookstore.service.AuthorService;
import lombok.AllArgsConstructor;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class AuthorController {

    private AuthorService authorService;

    @GetMapping(path = "/api/v1/authors")
    @PageableAsQueryParam
    public Page<AuthorDTO> getAll(Pageable pageable) {
        return authorService.getAll(pageable).map(AuthorMapper.INSTANCE::toDTO);
    }

    @GetMapping(path = "/api/v1/authors/{id}")
    public AuthorDTO getById(@PathVariable long id) throws ObjectNotFoundException {
        return AuthorMapper.INSTANCE.toDTO(authorService.getById(id));
    }

    @PostMapping(path = "/api/v1/authors")
    public AuthorDTO add(@RequestBody AddAuthorDTO dto) throws ObjectAlreadyExistsException {
        return AuthorMapper.INSTANCE.toDTO(authorService.add(dto));
    }
}
