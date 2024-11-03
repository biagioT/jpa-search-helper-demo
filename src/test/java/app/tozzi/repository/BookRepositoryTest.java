package app.tozzi.repository;

import app.tozzi.model.Book;
import app.tozzi.model.input.JPASearchInput;
import app.tozzi.repository.entity.BookEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void simpleCountTest() {
        assertTrue(bookRepository.count() > 0);
    }

    @Test
    public void mode1() {
        var books = bookRepository.findAll(Map.of("isbn_eq", "1234567890123456"), Book.class);
        assertNotNull(books);
        assertEquals(1, books.size());
        assertEquals("1234567890123456", books.getFirst().getIsbn());
    }

    @Test
    public void mode2() {
        var input = new JPASearchInput();
        input.setFilter(new JPASearchInput.RootFilter());
        input.getFilter().setFilters(new ArrayList<>());
        input.getFilter().setOperator("or");
        var f1 = new JPASearchInput.FilterSingleValue();
        f1.setValue("1234567890123456");
        f1.setOperator("eq");
        f1.setKey("isbn");
        var f2 = new JPASearchInput.FilterSingleValue();
        f2.setValue("2234567890123456");
        f2.setOperator("eq");
        f2.setKey("isbn");
        input.getFilter().getFilters().add(f1);
        input.getFilter().getFilters().add(f2);
        input.setOptions(new JPASearchInput.JPASearchOptions());
        input.getOptions().setSortKey("isbn");
        input.getOptions().setSortDesc(true);

        var books = bookRepository.findAllSorted(input, Book.class);
        assertNotNull(books);
        assertEquals(2, books.size());
        assertEquals("2234567890123456", books.getFirst().getIsbn());
        assertEquals("1234567890123456", books.getLast().getIsbn());
    }

    @Test
    public void mode1_projection() {
        var books = bookRepository.projection(Map.of("isbn_eq", "1234567890123456", "selections", "isbn"), Book.class, BookEntity.class);
        assertNotNull(books);
        assertEquals(1, books.size());
        assertEquals("1234567890123456", books.getFirst().get("isbn"));
        assertNull(books.getFirst().get("title"));
    }

    @Test
    public void mode2_projection() {
        var input = new JPASearchInput();
        input.setFilter(new JPASearchInput.RootFilter());
        input.getFilter().setFilters(new ArrayList<>());
        input.getFilter().setOperator("or");
        var f1 = new JPASearchInput.FilterSingleValue();
        f1.setValue("1234567890123456");
        f1.setOperator("eq");
        f1.setKey("isbn");
        var f2 = new JPASearchInput.FilterSingleValue();
        f2.setValue("2234567890123456");
        f2.setOperator("eq");
        f2.setKey("isbn");
        input.getFilter().getFilters().add(f1);
        input.getFilter().getFilters().add(f2);
        input.setOptions(new JPASearchInput.JPASearchOptions());
        input.getOptions().setSortKey("isbn");
        input.getOptions().setSortDesc(true);
        input.getOptions().setSelections(List.of("isbn"));

        var books = bookRepository.projectionWithSorting(input, Book.class, BookEntity.class);
        assertNotNull(books);
        assertEquals(2, books.size());
        assertEquals("2234567890123456", books.getFirst().get("isbn"));
        assertEquals("1234567890123456", books.getLast().get("isbn"));
    }
}
