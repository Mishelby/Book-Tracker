package org.example.booktracker.repository;

import org.example.booktracker.domain.bookGenre.BookGenreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreRepository extends JpaRepository<BookGenreEntity, Long> {
}
