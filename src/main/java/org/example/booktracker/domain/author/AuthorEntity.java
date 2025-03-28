package org.example.booktracker.domain.author;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.booktracker.domain.authorBook.AuthorBookEntity;
import org.example.booktracker.domain.city.CityEntity;
import org.example.booktracker.domain.user.UserEntity;

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

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AuthorBookEntity> books;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id")
    private CityEntity city;

    public AuthorEntity(
            String username,
            String email,
            String password,
            String name,
            String status,
            List<AuthorBookEntity> books
    ) {
        super(username, email, password);
        this.name = name;
        this.status = status;
        this.books = books;
    }

    public AuthorEntity(
            String username,
            String email,
            String password,
            String name,
            String status
    ) {
        super(username, email, password);
        this.name = name;
        this.status = status;
    }

    public AuthorEntity() {}
}
