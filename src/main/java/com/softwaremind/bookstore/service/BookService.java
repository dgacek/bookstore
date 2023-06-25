package com.softwaremind.bookstore.service;

import com.softwaremind.bookstore.exception.InvalidArgumentException;
import com.softwaremind.bookstore.exception.ObjectAlreadyExistsException;
import com.softwaremind.bookstore.exception.ObjectNotFoundException;
import com.softwaremind.bookstore.model.dto.AddBookDTO;
import com.softwaremind.bookstore.model.dto.UpdateBookDTO;
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

    @Transactional
    public Book update(UpdateBookDTO dto) throws InvalidArgumentException, ObjectNotFoundException, ObjectAlreadyExistsException {

        if (!isValidIsbn(dto.isbn())) {
            throw new InvalidArgumentException(String.format("%s is not a valid ISBN", dto.isbn()));
        }

        Book book = bookRepo.findById(dto.id())
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Book of id=%d does not exist", dto.id())));

        if (Boolean.TRUE.equals(bookRepo.existsByIsbn(dto.isbn())) && !dto.isbn().equals(book.getIsbn())) {
            throw new ObjectAlreadyExistsException(String.format("Book of isbn=%s already exists", dto.isbn()));
        }

        Author author = authorRepo.findById(dto.authorId())
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Author of id=%d does not exist", dto.authorId())));

        book.setIsbn(dto.isbn());
        book.setTitle(dto.title());
        book.setAuthor(author);

        return bookRepo.save(book);
    }

    @Transactional
    public void delete(long id) {
        Book book = bookRepo.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Book of id=%d does not exist", id)));
        bookRepo.delete(book);
    }

    private boolean isValidIsbn(String isbn) {
        return Pattern
                .compile("^(?:ISBN(?:-1[03])?:? )?(?=[0-9X]{10}$|(?=(?:[0-9]+[- ]){3})[- 0-9X]{13}$|97[89][0-9]{10}$|(?=(?:[0-9]+[- ]){4})[- 0-9]{17}$)(?:97[89][- ]?)?[0-9]{1,5}[- ]?[0-9]+[- ]?[0-9]+[- ]?[0-9X]$")
                .matcher(isbn)
                .find();
    }
}
