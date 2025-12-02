package app.tozzi.datafetcher;

import app.tozzi.model.Book;
import app.tozzi.repository.BookRepository;
import app.tozzi.repository.entity.BookEntity;
import app.tozzi.util.BookUtils;
import graphql.schema.DataFetchingFieldSelectionSet;
import io.micrometer.observation.annotation.Observed;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@Observed(name = "books", contextualName = "data-fetcher")
public class BookDataFetcher {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @QueryMapping
    public List<Book> projection(@Argument List<Filter> filters, DataFetchingFieldSelectionSet selectionSet) {

        if (filters == null || filters.isEmpty()) {
            filters = new ArrayList<>();
        }

        var filterMap = filters.stream().collect(Collectors.toMap(Filter::getKey, Filter::getValue));
        filterMap.put("selections", String.join(",", getSelections(selectionSet)));

        var mapList = bookRepository.projection(filterMap, Book.class, BookEntity.class);
        var entities = objectMapper.convertValue(mapList, new TypeReference<List<BookEntity>>() {
        });
        return entities.stream().map(BookUtils::toBook).toList();
    }

    private static Set<String> getSelections(DataFetchingFieldSelectionSet selectionSet) {
        return selectionSet.getFields().stream().filter(field -> field.getSelectionSet() == null || field.getSelectionSet().getImmediateFields().isEmpty())
                .map(field -> field.getQualifiedName().replace("/", ".")).collect(Collectors.toSet());
    }

    @Data
    public static class Filter {
        private String key;
        private String value;
    }

}
