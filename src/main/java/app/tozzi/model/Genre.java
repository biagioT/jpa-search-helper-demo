package app.tozzi.model;

import app.tozzi.annotation.Projectable;
import app.tozzi.annotation.Searchable;
import lombok.*;

@Data
@EqualsAndHashCode(of = {"id"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Genre {

    @Searchable
    private Long id;

    @Projectable(entityFieldKey = "authors.genres.description")
    @Searchable(entityFieldKey = "authors.genres.description")
    private String description;

}
