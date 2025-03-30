package org.example.booktracker.domain.quotes;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "daily_quote")
@Getter
@Setter
public class DailyQuoteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "author", nullable = false)
    private String author;

    @Column(name = "description", nullable = false)
    private String description;

    public DailyQuoteEntity(
            String title,
            String author,
            String description
    ) {
        this.title = title;
        this.author = author;
        this.description = description;
    }

    public DailyQuoteEntity() {}
}
