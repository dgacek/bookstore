package com.softwaremind.bookstore;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.softwaremind.bookstore.model.dto.AddBookDTO;
import com.softwaremind.bookstore.model.dto.BookDTO;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
class BookControllerIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AuthorRepo authorRepo;

    @Autowired
    private BookRepo bookRepo;

    @Autowired
    private ObjectMapper objectMapper;

    private final String apiUrl = "/api/v1/books";

    @Test
    void whenAddBook_GivenCorrectData_ShouldSetCorrectSearchString() {
        Author author = Author.builder()
                .name("Author Name")
                .build();
        authorRepo.save(author);
        AddBookDTO dto = new AddBookDTO("Book Title", "978-0-13-468599-1", 1);
        String expectedSearchString = (dto.title() + author.getName() + dto.isbn()).toLowerCase().replace(" ", "");
        BookDTO response;

        try {
            String responseString = mockMvc.perform(MockMvcRequestBuilders.post(apiUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                    .andExpect(MockMvcResultMatchers.status().isOk()).andReturn().getResponse().getContentAsString();
            response = objectMapper.readValue(responseString, BookDTO.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Book added = bookRepo.findById(response.id()).orElseThrow();
        Assertions.assertEquals(expectedSearchString, added.getSearchString());
    }
}
