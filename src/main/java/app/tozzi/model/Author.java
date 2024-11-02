package app.tozzi.model;

import app.tozzi.annotation.NestedProjectable;
import app.tozzi.annotation.NestedSearchable;
import app.tozzi.annotation.Projectable;
import app.tozzi.annotation.Searchable;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(of = {"id"})
@Builder
public class Author {

    @Searchable
    private Long id;

    @Projectable(entityFieldKey = "authors.fullName")
    @Searchable(entityFieldKey = "authors.fullName")
    private String name;

    @NestedProjectable
    @NestedSearchable
    private List<Genre> genres;

}
