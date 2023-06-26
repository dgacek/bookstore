package com.softwaremind.bookstore.controller;

import com.softwaremind.bookstore.exception.InvalidArgumentException;
import com.softwaremind.bookstore.exception.ObjectAlreadyExistsException;
import com.softwaremind.bookstore.exception.ObjectNotFoundException;
import com.softwaremind.bookstore.model.dto.AddAuthorDTO;
import com.softwaremind.bookstore.model.dto.AuthorDTO;
import com.softwaremind.bookstore.model.mapper.AuthorMapper;
import com.softwaremind.bookstore.service.AuthorService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@AllArgsConstructor
public class AuthorController extends LoggedController {

    private AuthorService authorService;

    @GetMapping(path = "/api/v1/authors")
    @PageableAsQueryParam
    public Page<AuthorDTO> getAll(@RequestParam(name = "search") Optional<String> search, Pageable pageable, HttpServletRequest request) {
        log(request);
        return authorService.getAll(search, pageable).map(AuthorMapper.INSTANCE::toDTO);
    }

    @GetMapping(path = "/api/v1/authors/{id}")
    public AuthorDTO getById(@PathVariable long id, HttpServletRequest request) throws ObjectNotFoundException {
        log(request);
        return AuthorMapper.INSTANCE.toDTO(authorService.getById(id));
    }

    @PostMapping(path = "/api/v1/authors")
    public AuthorDTO add(@RequestBody AddAuthorDTO dto, HttpServletRequest request) throws ObjectAlreadyExistsException {
        log(request);
        return AuthorMapper.INSTANCE.toDTO(authorService.add(dto));
    }

    @PutMapping(path = "/api/v1/authors")
    public AuthorDTO update(@RequestBody AuthorDTO dto, HttpServletRequest request) throws ObjectNotFoundException {
        log(request);
        return AuthorMapper.INSTANCE.toDTO(authorService.update(dto));
    }

    @DeleteMapping (path = "/api/v1/authors/{id}")
    public void delete(@PathVariable long id, HttpServletRequest request) throws ObjectNotFoundException, InvalidArgumentException {
        log(request);
        authorService.delete(id);
    }
}
