package app.tozzi.model;

import app.tozzi.annotation.NestedProjectable;
import app.tozzi.annotation.NestedSearchable;
import app.tozzi.annotation.Projectable;
import app.tozzi.annotation.Searchable;
import lombok.*;

import java.util.List;

@Data
@EqualsAndHashCode(of = {"id"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
