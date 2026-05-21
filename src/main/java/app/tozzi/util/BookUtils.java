package app.tozzi.util;

import app.tozzi.model.Author;
import app.tozzi.model.Book;
import app.tozzi.model.Genre;
import app.tozzi.repository.entity.AuthorEntity;
import app.tozzi.repository.entity.BookEntity;
import app.tozzi.repository.entity.GenresEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BookUtils {

    public static Book toBook(BookEntity entity) {
        return Book.builder()
                .isbn(entity.getIsbn())
                .title(entity.getTitle())
                .pages(entity.getPages())
                .copiesSold(entity.getTotalCopiesSold())
                .averageSellingPrice(entity.getAvgPrice())
                .publicationDate(entity.getPublicationDate())
                .authors(toAuthors(entity))
                .build();
    }

    private static List<Author> toAuthors(BookEntity entity) {
        if (entity.getAuthors() == null || entity.getAuthors().isEmpty()) {
            return Collections.emptyList();
        }
        return entity.getAuthors().stream().map(BookUtils::toAuthor).toList();
    }

    private static Author toAuthor(AuthorEntity authorEntity) {
        return Author.builder()
                .id(authorEntity.getId())
                .name(authorEntity.getFullName())
                .genres(toGenres(authorEntity))
                .build();
    }

    private static List<Genre> toGenres(AuthorEntity authorEntity) {
        if (authorEntity.getGenres() == null || authorEntity.getGenres().isEmpty()) {
            return Collections.emptyList();
        }
        return authorEntity.getGenres().stream().map(BookUtils::toGenre).toList();
    }

    private static Genre toGenre(GenresEntity genresEntity) {
        return Genre.builder()
                .id(genresEntity.getId())
                .description(genresEntity.getDescription())
                .build();
    }

}
