package app.tozzi.manager;

import app.tozzi.model.input.JPASearchInput;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class SQLInjectionTests {

    @Autowired
    private BookManager bookManager;

    private static final List<String> MALICIOUS_VALUES = List.of(
            "' OR '1'='1",
            "' OR '1'='1' --",
            "' OR '1'='1' /*",
            "\" OR \"1\"=\"1",
            "\" OR \"1\"=\"1\" --",
            "\" OR \"1\"=\"1\" /*",
            "test' --",
            "test' /*",
            "test' #",
            "' OR 1=1 --",
            "' OR 1=1 /*",
            "' OR 1=1 #",
            "' OR 'a'='a",
            "' AND 'a'='a",
            "' AND 1=1",
            "' AND 1=2",
            "'; DROP TABLE BOOKS; --",
            "'; SELECT * FROM BOOKS; --",
            "' UNION SELECT null/,null --",
            "' UNION SELECT 1/,2/,3/,4 --"
    );


    @Test
    public void sqlInjectionTests1() {
        MALICIOUS_VALUES.forEach(mv -> {
            var res = bookManager.findBooks(Map.of("title", mv, "_limit", "100"));
            assertTrue(res.isEmpty());
        });

    }

    @Test
    public void sqlInjectionTests2() {
        MALICIOUS_VALUES.forEach(mv -> {
            var input = new JPASearchInput();
            input.setOptions(new JPASearchInput.JPASearchOptions());
            input.getOptions().setPageSize(10);
            input.setFilter(new JPASearchInput.RootFilter());
            input.getFilter().setOperator("or");
            input.getFilter().setFilters(new ArrayList<>());
            var f = new JPASearchInput.FilterSingleValue();
            f.setOperator("eq");
            f.setKey("title");
            f.setValue(mv);
            input.getFilter().getFilters().add(f);
            var res = bookManager.findBooks(input);
            assertTrue(res.isEmpty());
        });

    }


}
