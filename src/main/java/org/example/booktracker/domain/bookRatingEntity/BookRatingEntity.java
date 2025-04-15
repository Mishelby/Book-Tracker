package org.example.booktracker.domain.bookRatingEntity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.booktracker.domain.book.BookEntity;

@Entity
@Table(name = "book_rating")
@Getter
@Setter
public class BookRatingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", referencedColumnName = "id", unique = true)
    private BookEntity book;

    @Column(name = "rating")
    private Float rating;

    public BookRatingEntity(
            BookEntity book,
            Float rating
    ) {
        this.book = book;
        this.rating = rating;
    }

    public BookRatingEntity() {}

}
