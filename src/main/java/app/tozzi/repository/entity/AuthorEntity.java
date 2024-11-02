package app.tozzi.repository.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "AUTHORS")
public class AuthorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "FULL_NAME")
    private String fullName;

    @ManyToMany
    @JoinTable(
            name = "AUTHORS_GENRES",
            joinColumns = @JoinColumn(name = "AUTHOR_ID"),
            inverseJoinColumns = @JoinColumn(name = "GENRE_ID")
    )
    private Set<GenresEntity> genres = new HashSet<>();

    @ManyToMany(mappedBy = "authors")
    private Set<BookEntity> books = new HashSet<>();

}
