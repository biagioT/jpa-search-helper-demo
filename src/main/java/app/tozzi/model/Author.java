package app.tozzi.model;

import app.tozzi.annotation.Searchable;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of = {"id"})
@Builder
public class Author {

    @Searchable
    private Long id;

    @Searchable(entityFieldKey = "authors.fullName")
    private String name;

}
