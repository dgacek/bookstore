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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.stream.Stream;

@ExtendWith(MockitoExtension.class)
class BookServiceTests {

    @Mock
    private BookRepo bookRepo;

    @Mock
    private AuthorRepo authorRepo;

    @InjectMocks
    private BookService bookService;


    @ParameterizedTest
    @ValueSource(strings = {"978-0-13-468599-1", "0-553-21311-9", "0-201-40998-X"})
    void whenAddBook_GivenCorrectData_ShouldReturnAddedBook(String isbn) {
        AddBookDTO dto = new AddBookDTO("title", isbn, 1);

        Mockito.when(bookRepo.existsByIsbn(Mockito.anyString()))
                .thenReturn(false);

        Mockito.when(authorRepo.findById(dto.authorId()))
                .thenReturn(Optional.of(Author.builder()
                        .id(dto.authorId())
                        .name("name")
                        .build()));

        Mockito.when(bookRepo.save(Mockito.any(Book.class)))
                .thenAnswer(AdditionalAnswers.returnsFirstArg());

        Book added = bookService.add(dto);

        Assertions.assertEquals(dto.title(), added.getTitle());
        Assertions.assertEquals(dto.isbn(), added.getIsbn());
        Assertions.assertEquals(dto.authorId(), added.getAuthor().getId());
    }

    @Test
    void whenAddBook_GivenInvalidIsbn_ShouldThrowException() {
        AddBookDTO dto = new AddBookDTO("title", "isbn", 1);

        Assertions.assertThrows(InvalidArgumentException.class, () -> bookService.add(dto));
    }

    @Test
    void whenAddBook_GivenIsbnNotUnique_ShouldThrowException() {
        AddBookDTO dto = new AddBookDTO("title", "978-0-13-468599-1", 1);

        Mockito.when(bookRepo.existsByIsbn(Mockito.anyString()))
                .thenReturn(true);

        Assertions.assertThrows(ObjectAlreadyExistsException.class, () -> bookService.add(dto));
    }

    @Test
    void whenAddBook_GivenIncorrectAuthorId_ShouldThrowException() {
        AddBookDTO dto = new AddBookDTO("title", "978-0-13-468599-1", 1);

        Mockito.when(bookRepo.existsByIsbn(Mockito.anyString()))
                .thenReturn(false);

        Mockito.when(authorRepo.findById(dto.authorId()))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(ObjectNotFoundException.class, () -> bookService.add(dto));
    }

    static Stream<Arguments> isbnExistsParameterGenerator() {
        return Stream.of(
                Arguments.of("978-0-13-468599-1", false),
                Arguments.of("0-553-21311-9", true)
        );
    }

    @ParameterizedTest
    @MethodSource("isbnExistsParameterGenerator")
    void whenUpdateBook_GivenCorrectData_ShouldReturnUpdatedBook(String newIsbn, boolean exists) {
        String existingIsbn = "0-553-21311-9";
        UpdateBookDTO dto = new UpdateBookDTO(1, "title", newIsbn, 1);

        Mockito.when(bookRepo.findById(dto.id()))
                .thenReturn(Optional.of(Book.builder()
                        .id(dto.id())
                        .title("nottitle")
                        .isbn(existingIsbn)
                        .author(Author.builder()
                                .id(2)
                                .name("name")
                                .build())
                        .build()));

        Mockito.when(bookRepo.existsByIsbn(newIsbn))
                .thenReturn(exists);

        Mockito.when(authorRepo.findById(dto.authorId()))
                .thenReturn(Optional.of(Author.builder()
                        .id(dto.authorId())
                        .name("notname")
                        .build()));

        Mockito.when(bookRepo.save(Mockito.any(Book.class)))
                .then(AdditionalAnswers.returnsFirstArg());

        Book updated = bookService.update(dto);

        Assertions.assertEquals(dto.id(), updated.getId());
        Assertions.assertEquals(dto.title(), updated.getTitle());
        Assertions.assertEquals(dto.isbn(), updated.getIsbn());
        Assertions.assertEquals(dto.authorId(), updated.getAuthor().getId());
    }

    @Test
    void whenUpdateBook_GivenInvalidIsbn_ShouldThrowException() {
        UpdateBookDTO dto = new UpdateBookDTO(1, "title", "isbn", 1);

        Assertions.assertThrows(InvalidArgumentException.class, () -> bookService.update(dto));
    }

    @Test
    void whenUpdateBook_GivenIncorrectId_ShouldThrowException() {
        UpdateBookDTO dto = new UpdateBookDTO(1, "title", "978-0-13-468599-1", 1);

        Mockito.when(bookRepo.findById(dto.id()))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(ObjectNotFoundException.class, () -> bookService.update(dto));
    }

    @Test
    void whenUpdateBook_GivenNewIsbnNotUnique_ShouldThrowException() {
        String newIsbn = "978-0-13-468599-1";
        String existingIsbn = "0-553-21311-9";
        UpdateBookDTO dto = new UpdateBookDTO(1, "title", newIsbn, 1);

        Mockito.when(bookRepo.findById(dto.id()))
                .thenReturn(Optional.of(Book.builder().id(1).isbn(existingIsbn).build()));

        Mockito.when(bookRepo.existsByIsbn(dto.isbn()))
                .thenReturn(true);

        Assertions.assertThrows(ObjectAlreadyExistsException.class, () -> bookService.update(dto));
    }

    @Test
    void whenUpdateBook_GivenIncorrectAuthorId_ShouldThrowException() {
        String newIsbn = "978-0-13-468599-1";
        String existingIsbn = "0-553-21311-9";
        UpdateBookDTO dto = new UpdateBookDTO(1, "title", newIsbn, 1);

        Mockito.when(bookRepo.findById(dto.id()))
                .thenReturn(Optional.of(Book.builder().id(1).isbn(existingIsbn).build()));

        Mockito.when(bookRepo.existsByIsbn(dto.isbn()))
                .thenReturn(false);

        Mockito.when(authorRepo.findById(dto.authorId()))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(ObjectNotFoundException.class, () -> bookService.update(dto));
    }
}
