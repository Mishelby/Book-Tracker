package org.example.booktracker.repository;

import org.example.booktracker.domain.AuthorBook.AuthorBookEntity;
import org.example.booktracker.utils.AuthorBookId;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorBookRepository extends JpaRepository<AuthorBookEntity, AuthorBookId> {

    @EntityGraph(attributePaths = {"author", "book"})
    @Query("""
            SELECT abe
            FROM AuthorBookEntity abe
            WHERE abe.author.id = :authorId
            AND abe.book.id = :bookId                                   
            """)
    Optional<AuthorBookEntity> findByAuthorAndBookId(
            @Param("authorId") Long authorId,
            @Param("bookId") Long bookId
    );

    @EntityGraph(attributePaths = {"author", "book"})
    @Query("""
            SELECT (COUNT(*) > 0)
            FROM AuthorBookEntity abe
            WHERE abe.author.id = :authorId
            AND abe.book.id = :bookId                                   
            """)
    boolean existsByAuthorAndBookId(
            @Param("authorId") Long authorId,
            @Param("bookId") Long bookId
    );
}
