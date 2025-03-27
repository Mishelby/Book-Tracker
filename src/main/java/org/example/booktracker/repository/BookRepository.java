package org.example.booktracker.repository;

import org.example.booktracker.domain.Book.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long> {

    @Query("""
            SELECT (COUNT(*) > 0)
            FROM BookEntity b
            WHERE b.article = :article                       
            """)
    boolean existsByArticle(@Param("article") String article);

    @Query("""
            SELECT (COUNT(*) > 0)
            FROM BookEntity b
            WHERE b.name = :name  
            AND b.description = :description                   
            """)
    boolean existsByNameAndDescription(
            @Param("name") String article,
            @Param("description") String description
    );
}
