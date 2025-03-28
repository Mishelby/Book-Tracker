package org.example.booktracker.domain.book;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.example.booktracker.domain.authorBook.AuthorBookEntity;

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

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AuthorBookEntity> authors;

    public BookEntity(
            String name,
            String description
    ) {
        this.name = name;
        this.description = description;
    }

    public BookEntity() {}
}
