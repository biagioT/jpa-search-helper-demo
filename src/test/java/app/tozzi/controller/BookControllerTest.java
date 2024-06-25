package app.tozzi.controller;

import app.tozzi.manager.BookManager;
import app.tozzi.model.Author;
import app.tozzi.model.Book;
import app.tozzi.model.input.JPASearchInput;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookManager bookManager;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String PATH = "/books";

    @BeforeEach
    void setUp() {
        Book b1 = Book.builder()
                .isbn("1234567891234567")
                .title("My First Book")
                .authors(List.of(Author.builder().name("Biagio").build()))
                .build();

        Book b2 = Book.builder()
                .isbn("2234567891234567")
                .title("My Second Book")
                .authors(List.of(Author.builder().name("Tozzi").build()))
                .build();

        when(bookManager.findBooks(anyMap())).thenReturn(List.of(b1));
        when(bookManager.findBooks(any(JPASearchInput.class))).thenReturn(List.of(b1, b2));
    }

    @Test
    void findGet() throws Exception {
        mvc.perform(get(PATH + "?isbn_eq=1234567891234567"))
                .andExpect(status().is(200))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
    }

    @Test
    void findPost() throws Exception {
        mvc.perform(post(PATH)
                        .content(objectMapper.writeValueAsString(new JPASearchInput()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
    }

}
