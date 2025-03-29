package org.example.booktracker.domain.bookGenre;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.booktracker.domain.book.BookGenre;

@Entity
@Table(name = "book_genre")
@Getter
@Setter
public class BookGenreEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "genre_name",nullable = false, length = 50)
    private BookGenre bookGenre;

    public BookGenreEntity(
            BookGenre bookGenre
    ) {
        this.bookGenre = bookGenre;
    }

    public BookGenreEntity() {}
}
