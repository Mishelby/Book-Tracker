package org.example.booktracker.repository;

import org.example.booktracker.domain.authorBook.AuthorBookEntity;
import org.example.booktracker.domain.book.BookEntity;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long> {

    @EntityGraph(attributePaths = {"authors", "genre"})
    @Query("""
            SELECT b
            FROM BookEntity b      
            LEFT JOIN FETCH b.rating
            """)
    List<BookEntity> findAllBooks(PageRequest pageable);

    @EntityGraph(attributePaths = {"authors"})
    @Query("""
            SELECT b
            FROM BookEntity b
            LEFT JOIN FETCH b.genre
            WHERE LOWER(b.name) LIKE LOWER(CONCAT(:prefix, '%'))                        
            """)
    List<BookEntity> findByPrefix(
            @Param("prefix") String prefix,
            PageRequest pageable
    );

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

    @EntityGraph(attributePaths = {"authors"})
    @Query("""
            SELECT be
            FROM BookEntity be
            JOIN AuthorBookEntity abe ON abe.author.id = :authorId
            WHERE abe.author.id = :authorId                                      
            """)
    List<BookEntity> findByAuthorId(@Param("authorId") Long authorId);

    @EntityGraph(attributePaths = {"authors"})
    @Query("""
            SELECT abe
            FROM AuthorBookEntity  abe   
            WHERE abe.author.id = :id                                           
            """)
    List<AuthorBookEntity> findBooksByAuthorId(@Param("id") Long id);


    @Query("""
            SELECT(COUNT(*))
            FROM BookEntity            
            """)
    Optional<Long> countAllBooks();

    @Query("""
            SELECT DISTINCT b.name
            FROM BookEntity b   
            JOIN AuthorBookEntity abe ON abe.author.id = :authorId   
            WHERE abe.author.id = :authorId                            
            """)
    Optional<List<String>> findBestBooks(Long authorId);

    @EntityGraph(attributePaths = {"authors", "genre"})
    @Query("""
            SELECT b
            FROM BookEntity b
            WHERE b.id IN (:bookIds)         
            """)
    List<BookEntity> findBooksById(List<Long> bookIds);
}
