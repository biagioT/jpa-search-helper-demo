package app.tozzi.repository.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.NaturalId;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "BOOKS")
public class BookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @NaturalId
    @Column(name = "ISBN")
    private String isbn;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "PAGES")
    private Integer pages;

    @Column(name = "PUB_DATE")
    private Date publicationDate;

    @ManyToMany
    @JoinTable(
            name = "BOOK_AUTHOR",
            joinColumns = @JoinColumn(name = "BOOK_ID"),
            inverseJoinColumns = @JoinColumn(name = "AUTHOR_ID")
    )
    private Set<AuthorEntity> authors = new HashSet<>();

    @OneToMany(mappedBy = "book")
    private Set<SalesEntity> sales = new HashSet<>();

    @Formula("(SELECT COUNT(s.ID) FROM SALES s WHERE s.book_id = id)")
    private Long totalCopiesSold;

    @Formula("(SELECT AVG(s.PRICE) FROM SALES s WHERE s.book_id = id)")
    private BigDecimal avgPrice;

}
