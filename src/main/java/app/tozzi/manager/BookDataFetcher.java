package app.tozzi.manager;

import app.tozzi.model.Book;
import app.tozzi.repository.BookRepository;
import app.tozzi.repository.entity.BookEntity;
import app.tozzi.util.BookUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsDataFetchingEnvironment;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import graphql.schema.DataFetchingFieldSelectionSet;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@DgsComponent
public class BookDataFetcher {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @DgsQuery
    public List<Book> projection(@InputArgument List<Filter> filters, DgsDataFetchingEnvironment dgsDataFetchingEnvironment) {

        if (filters == null || filters.isEmpty()) {
            filters = new ArrayList<>();
        }

        Map<String, String> filterMap = filters.stream()
                .collect(Collectors.toMap(Filter::getKey, Filter::getValue));

        filterMap.put("selections", String.join(",", getSelections(dgsDataFetchingEnvironment.getSelectionSet())));
        var mapList = bookRepository.projection(filterMap, Book.class, BookEntity.class);
        List<BookEntity> entities = objectMapper.convertValue(mapList, new TypeReference<>() {
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
