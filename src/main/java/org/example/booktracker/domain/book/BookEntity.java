package org.example.booktracker.domain.book;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.example.booktracker.domain.authorBook.AuthorBookEntity;
import org.example.booktracker.domain.bookGenre.BookGenreEntity;
import org.example.booktracker.domain.bookRatingEntity.BookRatingEntity;

import java.util.List;

@Entity
@Table(name = "book")
@Getter
@Setter
@ToString(of = {"name", "description", "article"})
public class BookEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "article", unique = true)
    private String article;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "genre_id")
    private BookGenreEntity genre;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AuthorBookEntity> authors;

    @OneToOne(mappedBy = "book", cascade = CascadeType.ALL)
    private BookRatingEntity rating;

    public BookEntity(
            String name,
            String description,
            BookGenreEntity bookGenreEntity
    ) {
        this.name = name;
        this.description = description;
        this.genre = bookGenreEntity;
    }

    public BookEntity() {
    }
}
