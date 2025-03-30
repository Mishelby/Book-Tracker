package org.example.booktracker.repository;

import org.example.booktracker.domain.authorRating.AuthorRatingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorRatingRepository extends JpaRepository<AuthorRatingEntity, Long> {

    @Query("""
            SELECT ar.rating
            FROM AuthorRatingEntity ar 
            WHERE ar.authorId = :authorId                      
            """)
    Optional<Double> findRatingByAuthorId(Long authorId);
}
