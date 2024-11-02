package app.tozzi.repository;

import app.tozzi.repository.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long>, JPASearchRepository<BookEntity>, JPAProjectionRepository<BookEntity> {
}
