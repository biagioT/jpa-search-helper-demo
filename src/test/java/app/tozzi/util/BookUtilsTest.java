package app.tozzi.util;

import app.tozzi.model.Book;
import app.tozzi.repository.entity.AuthorEntity;
import app.tozzi.repository.entity.BookEntity;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class BookUtilsTest {

    @Test
    public void simpleTest() {
        Date now = new Date();
        BookEntity be = new BookEntity();
        be.setId(1L);
        be.setIsbn("1234567891234567");
        be.setTitle("My First Book");
        be.setPages(30);
        be.setAvgPrice(new BigDecimal("33.55"));
        be.setPublicationDate(now);
        be.setTotalCopiesSold(13000L);
        AuthorEntity a1 = new AuthorEntity();
        a1.setId(1L);
        a1.setFullName("Biagio Tozzi");
        AuthorEntity a2 = new AuthorEntity();
        a2.setId(2L);
        a2.setFullName("Biagio P. Tozzi");
        be.setAuthors(Set.of(a1, a2));
        Book book = BookUtils.toBook(be);
        assertNotNull(book);
        assertEquals("1234567891234567", book.getIsbn());
        assertEquals("My First Book", book.getTitle());
        assertEquals(30, book.getPages());
        assertEquals(new BigDecimal("33.55"), book.getAverageSellingPrice());
        assertEquals(now, book.getPublicationDate());
        assertEquals(13000L, book.getCopiesSold());
        assertNotNull(book.getAuthors());
        assertEquals(2, book.getAuthors().size());
        assertTrue(book.getAuthors().stream().anyMatch(a -> a.getName().equals("Biagio Tozzi") && a.getId().equals(1L)));
        assertTrue(book.getAuthors().stream().anyMatch(a -> a.getName().equals("Biagio P. Tozzi") && a.getId().equals(2L)));

    }

}
