package app.tozzi.model;

import app.tozzi.annotation.Projectable;
import app.tozzi.annotation.Searchable;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of = {"id"})
@Builder
public class Genre {

    @Searchable
    private Long id;

    @Projectable(entityFieldKey = "authors.genres.description")
    @Searchable(entityFieldKey = "authors.genres.description")
    private String description;

}
