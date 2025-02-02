package app.tozzi.controller;

import app.tozzi.datafetcher.BookDataFetcher;
import app.tozzi.model.Book;
import app.tozzi.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class GraphQLTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BookDataFetcher bookDataFetcher;

    @MockitoBean
    private BookRepository bookRepository;

    @Test
    public void test_ok() throws Exception {
        var book = Book.builder()
                .isbn("1234567891234567")
                .build();

        when(bookRepository.projection(anyMap(), any(), any())).thenReturn(List.of(Map.of("isbn", "1234567890123456")));
        when(bookDataFetcher.projection(any(), any())).thenReturn(List.of(book));

        mockMvc.perform(MockMvcRequestBuilders.post("/graphql")
                        .contentType("application/json")
                        .content("{\"query\":\"query {\\n\\tprojection(\\n\\t\\tfilters: [{ key: \\\"isbn_in\\\", value: \\\"1234567890123456,2234567890123456\\\" }]\\n\\t) {\\n\\t\\tisbn\\n\\t}\\n}\\n\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.projection[0].isbn").exists());

        verify(bookDataFetcher, times(1)).projection(any(), any());
    }

    @Test
    public void test_ko() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/graphql")
                        .contentType("application/json")
                        .content("{\"query\":\"{\\n                projection(filters : [\\n                  { key: \\\"isbn_in\\\", value: \\\"1234567890123456,2234567890123456\\\" }\\n                ]) {\\n                    isbna\\n                }\\n            }\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.errors").exists())
                .andExpect(jsonPath("$.data").doesNotExist());

        verify(bookDataFetcher, times(0)).projection(any(), any());
    }

}
