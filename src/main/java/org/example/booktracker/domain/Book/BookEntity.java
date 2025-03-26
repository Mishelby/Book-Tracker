package org.example.booktracker.domain.Book;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.booktracker.domain.Author.AuthorEntity;

@Entity
@Table(name = "book")
@Getter
@Setter
public class BookEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private AuthorEntity author;

    @Column(name = "article")
    private String article;

    public BookEntity(
            String name,
            String description,
            AuthorEntity author
    ) {
        this.name = name;
        this.description = description;
        this.author = author;
    }

    public BookEntity() {}
}
