package com.softwaremind.bookstore.controller;

import com.softwaremind.bookstore.exception.InvalidArgumentException;
import com.softwaremind.bookstore.exception.ObjectAlreadyExistsException;
import com.softwaremind.bookstore.exception.ObjectNotFoundException;
import com.softwaremind.bookstore.model.dto.AddAuthorDTO;
import com.softwaremind.bookstore.model.dto.AuthorDTO;
import com.softwaremind.bookstore.model.mapper.AuthorMapper;
import com.softwaremind.bookstore.service.AuthorService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class AuthorController {

    private AuthorService authorService;

    private final Marker marker = MarkerFactory.getMarker("API");
    private final Logger logger = LoggerFactory.getLogger(getClass());



    @GetMapping(path = "/api/v1/authors")
    @PageableAsQueryParam
    public Page<AuthorDTO> getAll(Pageable pageable) {
        logger.info(marker, "GET /api/v1/authors");
        return authorService.getAll(pageable).map(AuthorMapper.INSTANCE::toDTO);
    }

    @GetMapping(path = "/api/v1/authors/{id}")
    public AuthorDTO getById(@PathVariable long id) throws ObjectNotFoundException {
        logger.info(marker, "GET /api/v1/authors/{}", id);
        return AuthorMapper.INSTANCE.toDTO(authorService.getById(id));
    }

    @PostMapping(path = "/api/v1/authors")
    public AuthorDTO add(@RequestBody AddAuthorDTO dto) throws ObjectAlreadyExistsException {
        logger.info(marker, "POST /api/v1/authors");
        return AuthorMapper.INSTANCE.toDTO(authorService.add(dto));
    }

    @PutMapping(path = "/api/v1/authors")
    public AuthorDTO update(@RequestBody AuthorDTO dto) throws ObjectNotFoundException {
        logger.info(marker, "PUT /api/v1/authors");
        return AuthorMapper.INSTANCE.toDTO(authorService.update(dto));
    }

    @DeleteMapping (path = "/api/v1/authors/{id}")
    public void delete(@PathVariable long id) throws ObjectNotFoundException, InvalidArgumentException {
        logger.info(marker, "DELETE /api/v1/authors/{}", id);
        authorService.delete(id);
    }
}
