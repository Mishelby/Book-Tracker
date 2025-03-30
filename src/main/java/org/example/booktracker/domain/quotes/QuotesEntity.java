package org.example.booktracker.domain.quotes;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "quotes")
@Getter
@Setter
public class QuotesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;

    private String author;

    private String category;

    private LocalDateTime created_at;

    public QuotesEntity(String text, String author, String category) {
        this.text = text;
        this.author = author;
        this.category = category;
    }

    public QuotesEntity() {}

    @PrePersist
    public void prePersist() {
        this.created_at = LocalDateTime.now();
    }
}
