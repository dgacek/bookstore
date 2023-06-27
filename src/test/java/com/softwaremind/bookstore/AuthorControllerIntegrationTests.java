package com.softwaremind.bookstore;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.softwaremind.bookstore.model.dto.AddAuthorDTO;
import com.softwaremind.bookstore.model.dto.AuthorDTO;
import com.softwaremind.bookstore.model.entity.Author;
import com.softwaremind.bookstore.model.entity.Book;
import com.softwaremind.bookstore.model.repo.AuthorRepo;
import com.softwaremind.bookstore.model.repo.BookRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD) // this is terrible
class AuthorControllerIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AuthorRepo authorRepo;

    @Autowired
    private BookRepo bookRepo;

    @Autowired
    private ObjectMapper objectMapper;

    private final String apiUrl = "/api/v1/authors";

    @Test
    void whenAddAuthor_GivenCorrectData_ShouldSetCorrectSearchString() {
        AddAuthorDTO dto = new AddAuthorDTO("Author Name");
        String expectedSearchString = dto.name().toLowerCase().replace(" ", "");
        AuthorDTO response;

        try {
            String responseString = mockMvc.perform(MockMvcRequestBuilders.post(apiUrl)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(dto)))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn()
                    .getResponse()
                    .getContentAsString();
            response = objectMapper.readValue(responseString, AuthorDTO.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Author added = authorRepo.findById(response.id()).orElseThrow();
        Assertions.assertEquals(expectedSearchString, added.getSearchString());
    }

    @Test
    void whenUpdateAuthor_GivenAuthorHasAssignedBooks_ShouldSetSearchStringForAllAssignedBooks() {
        Author author = Author.builder().name("Old Name").build();
        author = authorRepo.save(author);
        Book book1 = Book.builder()
                .isbn("978-0-13-468599-1")
                .title("Title 1")
                .author(author)
                .build();
        Book book2 = Book.builder()
                .isbn("978-0-13-468599-2")
                .title("Title 2")
                .author(author)
                .build();
        bookRepo.saveAll(Arrays.asList(book1, book2));
        AuthorDTO dto = new AuthorDTO(author.getId(), "Updated Name");

        try {
            mockMvc.perform(MockMvcRequestBuilders.put(apiUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                    .andExpect(MockMvcResultMatchers.status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Author updated = authorRepo.findById(author.getId()).orElseThrow();
        Assertions.assertEquals(2, updated.getBooks().size());
        updated.getBooks().forEach(book -> Assertions.assertEquals((book.getTitle() + book.getAuthor().getName() + book.getIsbn()).toLowerCase().replace(" ", ""), book.getSearchString()));
    }
}
