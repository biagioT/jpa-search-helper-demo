package app.tozzi.model;

import app.tozzi.annotation.NestedSearchable;
import app.tozzi.annotation.Searchable;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@EqualsAndHashCode(of = "isbn")
@Builder
public class Book {

    @Searchable
    private String isbn;

    @Searchable
    private String title;

    @Searchable
    private Integer pages;

    @Searchable(datePattern = "yyyy-MM-dd")
    private Date publicationDate;

    @NestedSearchable
    private List<Author> authors;

    @Searchable(entityFieldKey = "totalCopiesSold")
    private Long copiesSold;

    @Searchable(entityFieldKey = "avgPrice")
    private BigDecimal averageSellingPrice;

}
