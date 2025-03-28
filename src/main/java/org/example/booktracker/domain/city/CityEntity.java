package org.example.booktracker.domain.city;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.example.booktracker.domain.author.AuthorEntity;

import java.util.List;

@Entity
@Table(name = "city")
@Getter
@Setter
@ToString(of = {"name"})
public class CityEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @OneToMany(mappedBy = "city", fetch = FetchType.LAZY)
    private List<AuthorEntity> authors;

    public CityEntity(String name) {
        this.name = name;
    }

    public CityEntity() {}
}
