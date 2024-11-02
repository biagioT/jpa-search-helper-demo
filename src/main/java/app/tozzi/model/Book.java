package app.tozzi.model;

import app.tozzi.annotation.NestedProjectable;
import app.tozzi.annotation.NestedSearchable;
import app.tozzi.annotation.Projectable;
import app.tozzi.annotation.Searchable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@EqualsAndHashCode(of = "isbn")
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public class Book {

    @Projectable
    @Searchable
    private String isbn;

    @Projectable
    @Searchable
    private String title;

    @Projectable
    @Searchable
    private Integer pages;

    @Projectable
    @Searchable(datePattern = "yyyy-MM-dd")
    private Date publicationDate;

    @NestedProjectable
    @NestedSearchable
    private List<Author> authors;

    @Projectable(entityFieldKey = "totalCopiesSold")
    @Searchable(entityFieldKey = "totalCopiesSold")
    private Long copiesSold;

    @Projectable(entityFieldKey = "avgPrice")
    @Searchable(entityFieldKey = "avgPrice")
    private BigDecimal averageSellingPrice;

}
