package org.example.booktracker.repository;

import org.example.booktracker.domain.bookRatingEntity.BookRatingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRatingRepository extends JpaRepository<BookRatingEntity, Long> {
}
