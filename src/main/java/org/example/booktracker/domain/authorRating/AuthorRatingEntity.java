package org.example.booktracker.domain.authorRating;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "author_rating")
@Getter
@Setter
public class AuthorRatingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "author_id", nullable = false)
    private Long authorId;

    @Column(name = "rating")
    private Double rating;

    public AuthorRatingEntity(
            Long authorId,
            Double rating
    ) {
        this.authorId = authorId;
        this.rating = rating;
    }

    public AuthorRatingEntity() {}
}
