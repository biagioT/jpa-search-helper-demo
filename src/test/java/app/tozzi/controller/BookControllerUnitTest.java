package app.tozzi.controller;

import app.tozzi.exception.JPASearchException;
import app.tozzi.manager.BookManager;
import app.tozzi.model.Author;
import app.tozzi.model.Book;
import app.tozzi.model.input.JPASearchInput;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
@AutoConfigureMockMvc
public class BookControllerUnitTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookManager bookManager;

    private static final String PATH = "/books";

    private static final String JSON_OK = """
            {
                "filter": {
                    "operator": "or",
                    "filters": [
                        {
                            "operator": "eq",
                            "value": "1034567890123456",
                            "key": "isbn"
                        },
                        {
                            "operator": "in",
                            "values": [
                                "1034567890123456",
                                "1234567890123456"
                            ],
                            "key": "isbn"
                        },
                        {
                            "operator": "and",
                            "filters": [
                                {
                                    "operator": "null",
                                    "key": "title",
                                    "options": {
                                        "negate": false
                                    }
                                },
                                {
                                    "operator": "lt",
                                    "key": "pages",
                                    "value": 10
                                }
                            ]
                        }
                    ]
                },
                "options": {
                    "pageSize": 4
                }
            }
            """;

    private static final String JSON_400 = """
            {
                "options": {
                    "pageSize": 4
                }
            }
            """;

    @Test
    public void mode1_success() throws Exception {
        var book = Book.builder()
                .isbn("1234567891234567")
                .title("My First Book")
                .authors(List.of(Author.builder().name("Biagio").build()))
                .build();

        when(bookManager.findBooks(anyMap())).thenReturn(List.of(book));
        mvc.perform(get(PATH + "?isbn_eq=1234567891234567")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
        verify(bookManager, times(1)).findBooks(anyMap());
    }

    @Test
    public void mode2_success() throws Exception {
        var book1 = Book.builder()
                .isbn("1234567891234567")
                .title("My First Book")
                .authors(List.of(Author.builder().name("Biagio").build()))
                .build();

        var book2 = Book.builder()
                .isbn("2234567891234567")
                .title("My Second Book")
                .authors(List.of(Author.builder().name("Tozzi").build()))
                .build();

        when(bookManager.findBooks(any(JPASearchInput.class))).thenReturn(List.of(book1, book2));
        mvc.perform(post(PATH)
                        .content(JSON_OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
        verify(bookManager, times(1)).findBooks(any(JPASearchInput.class));
    }

    @Test
    public void mode1_400_1() throws Exception {
        when(bookManager.findBooks(anyMap())).thenThrow(JPASearchException.class);

        mvc.perform(get(PATH + "?isbn_eq=1234567891234567")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400));

        verify(bookManager, times(1)).findBooks(anyMap());
    }

    @Test
    public void mode2_400_1() throws Exception {
        mvc.perform(post(PATH)
                        .content(JSON_400)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400));

        verify(bookManager, times(0)).findBooks(anyMap());
    }

    @Test
    public void mode2_400_2() throws Exception {
        when(bookManager.findBooks(any(JPASearchInput.class))).thenThrow(JPASearchException.class);

        mvc.perform(post(PATH)
                        .content(JSON_OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400));

        verify(bookManager, times(1)).findBooks(any(JPASearchInput.class));
    }
}
