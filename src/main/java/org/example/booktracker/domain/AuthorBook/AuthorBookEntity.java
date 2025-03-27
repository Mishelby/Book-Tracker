package org.example.booktracker.domain.AuthorBook;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.booktracker.domain.Author.AuthorEntity;
import org.example.booktracker.domain.Book.BookEntity;
import org.example.booktracker.utils.AuthorBookId;

@Entity
@Table(name = "author_book")
@Getter
@Setter
@IdClass(AuthorBookId.class)
public class AuthorBookEntity {
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private AuthorEntity author;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    private BookEntity book;

    public AuthorBookEntity(
            AuthorEntity author,
            BookEntity book
    ) {
        this.author = author;
        this.book = book;
    }

    public AuthorBookEntity() {}

}
