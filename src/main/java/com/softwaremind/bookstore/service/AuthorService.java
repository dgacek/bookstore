package com.softwaremind.bookstore.service;

import com.softwaremind.bookstore.exception.InvalidArgumentException;
import com.softwaremind.bookstore.exception.ObjectAlreadyExistsException;
import com.softwaremind.bookstore.exception.ObjectNotFoundException;
import com.softwaremind.bookstore.model.dto.AddAuthorDTO;
import com.softwaremind.bookstore.model.dto.AuthorDTO;
import com.softwaremind.bookstore.model.entity.Author;
import com.softwaremind.bookstore.model.repo.AuthorRepo;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AuthorService {

    private AuthorRepo authorRepo;

    public Page<Author> getAll(Pageable pageable) {
        return authorRepo.findAll(pageable);
    }

    public Author getById(long id) throws ObjectNotFoundException {
        return authorRepo.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Author of id=%d does not exist", id)));
    }

    @Transactional
    public Author add(AddAuthorDTO dto) throws ObjectAlreadyExistsException {
        if (Boolean.TRUE.equals(authorRepo.existsByName(dto.name()))) {
            throw new ObjectAlreadyExistsException(String.format("Author with name=%s already exists", dto.name()));
        }
        return authorRepo.save(Author.builder()
                .name(dto.name())
                .build());
    }

    @Transactional
    public Author update(AuthorDTO dto) throws ObjectAlreadyExistsException {
        Author author = authorRepo.findById(dto.id())
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Author of id=%d does not exist", dto.id())));
        author.setName(dto.name());
        return authorRepo.save(author);
    }

    @Transactional
    public void delete(long id) throws ObjectNotFoundException, InvalidArgumentException {
        Author author = authorRepo.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Author of id=%d does not exist", id)));
        if (!author.getBooks().isEmpty()) {
            throw new InvalidArgumentException(String.format("Author of id=%d is assigned to books id=[%s] and can't be removed", id, author.getBooks().stream()
                    .map(i -> String.valueOf(i.getId()))
                    .collect(Collectors.joining(","))));
        }
        authorRepo.delete(author);
    }
}
