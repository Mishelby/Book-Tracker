package org.example.booktracker.repository;

import org.example.booktracker.domain.author.AuthorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<AuthorEntity, Long> {

    @Query("""
            SELECT (COUNT(*))
            FROM AuthorEntity
            """)
    Optional<Long> countAllAuthors();

    @Query("""
            SELECT at
            FROM AuthorEntity at
            JOIN AuthorRatingEntity are ON at.id = are.authorId
            WHERE are.rating > :rating   
            ORDER BY are.rating DESC
            LIMIT 10                                           
            """)
    List<AuthorEntity> findBestAuthorsByRating(
            Double rating
    );
}
