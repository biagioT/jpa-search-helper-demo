package app.tozzi.manager;

import app.tozzi.model.input.JPASearchInput;
import app.tozzi.repository.BookRepository;
import app.tozzi.repository.entity.AuthorEntity;
import app.tozzi.repository.entity.BookEntity;
import app.tozzi.repository.entity.GenresEntity;
import app.tozzi.util.BookUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookManagerUnitTest {

    @InjectMocks
    private BookManager bookManager;

    @Mock
    private BookRepository bookRepository;

    @Test
    public void mode1() {

        var now = new Date();
        var be = new BookEntity();
        be.setId(1L);
        be.setIsbn("1234567891234567");
        be.setTitle("My First Book");
        be.setPages(30);
        be.setAvgPrice(new BigDecimal("33.55"));
        be.setPublicationDate(now);
        be.setTotalCopiesSold(13000L);

        var a1 = new AuthorEntity();
        a1.setId(1L);
        a1.setFullName("Biagio Tozzi");

        var a2 = new AuthorEntity();
        a2.setId(2L);
        a2.setFullName("Biagio P. Tozzi");
        be.setAuthors(Set.of(a1, a2));

        var g1 = new GenresEntity();
        g1.setId(1L);
        g1.setDescription("Fantasy");

        var g2 = new GenresEntity();
        g2.setId(1L);
        g2.setDescription("Horror");

        a1.getGenres().add(g1);
        a1.getGenres().add(g2);
        a2.getGenres().add(g2);

        var page = new PageImpl<>(List.of(be));
        when(bookRepository.findAllWithPaginationAndSorting(anyMap(), any())).thenReturn(page);
        try (var mockedStatic = mockStatic(BookUtils.class)) {
            var books = bookManager.findBooks(new HashMap<>());
            assertNotNull(books);
            assertEquals(1, books.size());
            verify(bookRepository, times(1)).findAllWithPaginationAndSorting(anyMap(), any());
            mockedStatic.verify(() -> BookUtils.toBook(any()), times(1));
        }
    }

    @Test
    public void mode2() {

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

        var page = new PageImpl<>(List.of(be));
        when(bookRepository.findAllWithPaginationAndSorting(any(JPASearchInput.class), any())).thenReturn(page);
        try (var mockedStatic = mockStatic(BookUtils.class)) {
            var books = bookManager.findBooks(new JPASearchInput());
            assertNotNull(books);
            assertEquals(1, books.size());
            verify(bookRepository, times(1)).findAllWithPaginationAndSorting(any(JPASearchInput.class), any());
            mockedStatic.verify(() -> BookUtils.toBook(any()), times(1));
        }
    }

}
