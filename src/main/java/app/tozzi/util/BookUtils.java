package app.tozzi.util;

import app.tozzi.model.Author;
import app.tozzi.model.Book;
import app.tozzi.repository.entity.AuthorEntity;
import app.tozzi.repository.entity.BookEntity;

public class BookUtils {

    public static Book toBook(BookEntity entity) {
        return Book.builder()
                .isbn(entity.getIsbn())
                .title(entity.getTitle())
                .pages(entity.getPages())
                .copiesSold(entity.getTotalCopiesSold())
                .averageSellingPrice(entity.getAvgPrice())
                .publicationDate(entity.getPublicationDate())
                .authors(entity.getAuthors().stream().map(BookUtils::toAuthor).toList())
                .build();
    }

    public static Author toAuthor(AuthorEntity authorEntity) {
        return Author.builder()
                .id(authorEntity.getId())
                .name(authorEntity.getFullName())
                .build();
    }

}
