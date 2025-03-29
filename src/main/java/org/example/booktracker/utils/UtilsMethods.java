package org.example.booktracker.utils;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.booktracker.domain.city.CityDto;
import org.example.booktracker.exception.AuthorBookAlreadyExists;
import org.example.booktracker.exception.BookAlreadyExists;
import org.example.booktracker.exception.UserAlreadyExists;
import org.example.booktracker.repository.AuthorBookRepository;
import org.example.booktracker.repository.BookRepository;
import org.example.booktracker.repository.CityRepository;
import org.example.booktracker.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UtilsMethods {
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final CityRepository cityRepository;
    private final AuthorBookRepository authorBookRepository;

    public void isExistsByEmail(
            String email
    ) {
        if (userRepository.isExists(email)) throw new UserAlreadyExists(
                "User with email = %s already exists!"
                        .formatted(email)
        );
    }

    public void isExistsByNameAndDescription(
            String name,
            String description
    ) {
        if (bookRepository.existsByNameAndDescription(name, description)) throw new BookAlreadyExists(
                "Book with name = %s and description = %s already exists!"
                        .formatted(name, description)
        );
    }

    public void isExistsByAuthorAndBookId(
            Long authorId,
            Long bookId
    ) {
        if (authorBookRepository.existsByAuthorAndBookId(authorId, bookId)) throw new AuthorBookAlreadyExists(
                "Author with id = %s already exists book with id = %s!".formatted(authorId, bookId)
        );
    }

    public CityDto getCityByAuthorId(
            Long id
    ){
        var cityEntity = cityRepository.findByAuthorId(id).orElseThrow(
                () -> new EntityNotFoundException("City for author with id = %s not found".formatted(id))
        );

        return new CityDto(cityEntity.getName());
    }

}
