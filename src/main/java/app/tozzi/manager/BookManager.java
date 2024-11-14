package app.tozzi.manager;

import app.tozzi.model.Book;
import app.tozzi.model.input.JPASearchInput;
import app.tozzi.repository.BookRepository;
import app.tozzi.util.BookUtils;
import io.micrometer.observation.annotation.Observed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Observed(name = "books", contextualName = "manager")
public class BookManager {

    @Autowired
    private BookRepository bookRepository;

    @Transactional(readOnly = true)
    public List<Book> findBooks(Map<String, String> filters) {
        return bookRepository.findAllWithPaginationAndSorting(filters, Book.class).map(BookUtils::toBook).toList();
    }

    @Transactional(readOnly = true)
    public List<Book> findBooks(JPASearchInput input) {
        return bookRepository.findAllWithPaginationAndSorting(input, Book.class).map(BookUtils::toBook).toList();
    }
}
