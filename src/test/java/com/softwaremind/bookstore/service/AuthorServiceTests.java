package com.softwaremind.bookstore.service;

import com.softwaremind.bookstore.exception.ObjectAlreadyExistsException;
import com.softwaremind.bookstore.exception.ObjectNotFoundException;
import com.softwaremind.bookstore.model.dto.AddAuthorDTO;
import com.softwaremind.bookstore.model.dto.AuthorDTO;
import com.softwaremind.bookstore.model.entity.Author;
import com.softwaremind.bookstore.model.entity.Book;
import com.softwaremind.bookstore.model.repo.AuthorRepo;
import com.softwaremind.bookstore.model.repo.BookRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class AuthorServiceTests {

    @Mock
    AuthorRepo authorRepo;

    @Mock
    BookRepo bookRepo;

    @InjectMocks
    AuthorService authorService;

    @Test
    void whenAddAuthor_GivenCorrectData_ShouldReturnAuthor() {
        AddAuthorDTO dto = new AddAuthorDTO("testname");

        Mockito.when(authorRepo.save(Mockito.any(Author.class)))
                .then(AdditionalAnswers.returnsFirstArg());

        Mockito.when(bookRepo.save(Mockito.any(Book.class)))
                .then(AdditionalAnswers.returnsFirstArg());

        Author created = authorService.add(dto);

        Assertions.assertEquals(dto.name(), created.getName());
    }

    @Test
    void whenAddAuthor_GivenNameNotUnique_ShouldThrowException() {
        AddAuthorDTO dto = new AddAuthorDTO("testname");

        Mockito.when(authorRepo.existsByName(dto.name()))
                .thenReturn(true);

        Assertions.assertThrows(ObjectAlreadyExistsException.class, () -> authorService.add(dto));
    }

    @Test
    void whenUpdateAuthor_GivenCorrectData_ShouldReturnUpdatedAuthor() {
        AuthorDTO dto = new AuthorDTO(1, "testname");

        Mockito.when(authorRepo.findById(dto.id()))
                .thenReturn(Optional.of(Author.builder()
                        .id(dto.id())
                        .name("nottestname")
                        .build()));

        Mockito.when(authorRepo.save(Mockito.any(Author.class)))
                .then(AdditionalAnswers.returnsFirstArg());

        Mockito.when(bookRepo.save(Mockito.any(Book.class)))
                .then(AdditionalAnswers.returnsFirstArg());

        Author updated = authorService.update(dto);

        Assertions.assertEquals(dto.id(), updated.getId());
        Assertions.assertEquals(dto.name(), updated.getName());
    }

    @Test
    void whenUpdateAuthor_GivenIncorrectID_ShouldThrowException() {
        AuthorDTO dto = new AuthorDTO(1, "testname");

        Mockito.when(authorRepo.findById(dto.id()))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(ObjectNotFoundException.class, () -> authorService.update(dto));
    }

    @Test
    void whenUpdateAuthor_GivenNameNotUnique_ShouldThrowException() {
        AuthorDTO dto = new AuthorDTO(1, "testname");

        Mockito.when(authorRepo.findById(dto.id()))
                .thenReturn(Optional.of(Author.builder().id(dto.id()).name("nottestname").build()));

        Mockito.when(authorRepo.existsByName(dto.name()))
                .thenReturn(true);

        Assertions.assertThrows(ObjectAlreadyExistsException.class, () -> authorService.update(dto));
    }
}
