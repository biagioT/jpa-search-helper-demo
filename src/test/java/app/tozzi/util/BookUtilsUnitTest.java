package app.tozzi.util;

import app.tozzi.model.Book;
import app.tozzi.repository.entity.AuthorEntity;
import app.tozzi.repository.entity.BookEntity;
import app.tozzi.repository.entity.GenresEntity;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class BookUtilsUnitTest {

    @Test
    public void toBook() {
        Date now = new Date();
        var be = new BookEntity();
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

        GenresEntity g1 = new GenresEntity();
        g1.setId(1L);
        g1.setDescription("Fantasy");

        GenresEntity g2 = new GenresEntity();
        g2.setId(1L);
        g2.setDescription("Horror");

        a1.getGenres().add(g1);
        a1.getGenres().add(g2);
        a2.getGenres().add(g2);

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
        assertTrue(book.getAuthors().stream().anyMatch(a -> a.getName().equals("Biagio Tozzi") && a.getId().equals(1L) && a.getGenres().size() == 2
                && a.getGenres().stream().anyMatch(g -> g.getDescription().equals("Fantasy")) && a.getGenres().stream().anyMatch(g -> g.getDescription().equals("Horror"))));
        assertTrue(book.getAuthors().stream().anyMatch(a -> a.getName().equals("Biagio P. Tozzi") && a.getId().equals(2L) && a.getGenres().size() == 1 && a.getGenres().getFirst().getDescription().equals("Horror")));

    }

}
