package org.example.booktracker.domain.quotes;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "quotes")
public class QuotesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;

    private String author;

    private String category;

    private LocalDateTime created_at;

}
