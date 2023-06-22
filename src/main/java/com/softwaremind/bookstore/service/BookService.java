package com.softwaremind.bookstore.service;

import com.softwaremind.bookstore.exception.InvalidArgumentException;
import com.softwaremind.bookstore.exception.ObjectAlreadyExistsException;
import com.softwaremind.bookstore.exception.ObjectNotFoundException;
import com.softwaremind.bookstore.model.dto.AddBookDTO;
import com.softwaremind.bookstore.model.entity.Author;
import com.softwaremind.bookstore.model.entity.Book;
import com.softwaremind.bookstore.model.repo.AuthorRepo;
import com.softwaremind.bookstore.model.repo.BookRepo;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.regex.Pattern;

@Service
@AllArgsConstructor
public class BookService {

    private BookRepo bookRepo;
    private AuthorRepo authorRepo;

    public Page<Book> getAll(Pageable pageable) {
        return bookRepo.findAll(pageable);
    }

    public Book getById(long id) throws ObjectNotFoundException {
        return bookRepo.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Book of id=%d does not exist", id)));
    }

    @Transactional
    public Book add(AddBookDTO dto) throws ObjectAlreadyExistsException, ObjectNotFoundException, InvalidArgumentException {

        if (!isValidIsbn(dto.isbn())) {
            throw new InvalidArgumentException(String.format("%s is not a valid ISBN", dto.isbn()));
        }
        if (Boolean.TRUE.equals(bookRepo.existsByIsbn(dto.isbn()))) {
            throw new ObjectAlreadyExistsException(String.format("Book of isbn=%s already exists", dto.isbn()));
        }

        Author author = authorRepo.findById(dto.authorId())
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Author of id=%d does not exist", dto.authorId())));

        return bookRepo.save(Book.builder()
                .title(dto.title())
                .isbn(dto.isbn())
                .author(author)
                .build());
    }

    private boolean isValidIsbn(String isbn) {
        return Pattern
                .compile("^(?=[0-9]{13}$|(?=(?:[0-9]+[-\\ ]){4})[-\\ 0-9]{17}$)97[89][-\\ ]?[0-9]{1,5}[-\\ ]?[0-9]+[-\\ ]?[0-9]+[-\\ ]?[0-9]$")
                .matcher(isbn)
                .find();
    }
}
