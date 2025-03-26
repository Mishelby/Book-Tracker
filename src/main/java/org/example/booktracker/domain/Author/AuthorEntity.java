package org.example.booktracker.domain.Author;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.booktracker.domain.Book.BookEntity;
import org.example.booktracker.domain.User.UserEntity;

import java.util.List;

@Entity
@Table(name = "author")
@Getter
@Setter
public class AuthorEntity extends UserEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "status")
    private String status;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<BookEntity> books;

    public AuthorEntity(
            String username,
            String email,
            String password,
            String name,
            String status,
            List<BookEntity> books
    ) {
        super(username, email, password);
        this.name = name;
        this.status = status;
        this.books = books;
    }

    public AuthorEntity() {}
}
