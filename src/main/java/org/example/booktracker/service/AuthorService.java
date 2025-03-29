package org.example.booktracker.service;

import lombok.RequiredArgsConstructor;
import org.example.booktracker.domain.author.AuthorProfileDto;
import org.example.booktracker.exception.AuthorNotFoundException;
import org.example.booktracker.exception.BookNotFoundException;
import org.example.booktracker.mapper.AuthorBookMapper;
import org.example.booktracker.mapper.SuccessCreatedMapper;
import org.example.booktracker.repository.AuthorRepository;
import org.example.booktracker.repository.BookRepository;
import org.example.booktracker.utils.*;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class AuthorService {
    final Logger logger = Logger.getLogger(AuthorService.class.getName());
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final AuthorBookMapper authorBookMapper;
    private final WeatherService weatherService;

    // Utils
    private final UtilsMethods utilsMethods;
    private final SuccessCreatedMapper successCreatedMapper;

    @Transactional(readOnly = true)
    @Cacheable(value = "authorProfile", key = "#id")
    public AuthorProfileDto findAuthorProfile(
            Long id
    ) {
        logger.info(() -> "Fetching author profile for id = %s:".formatted(id));
        var authorEntity = authorRepository.findById(id).orElseThrow();
        var weatherDTO = weatherService.getWeather(new CityRequest("Moscow"));

        return new AuthorProfileDto(
                authorEntity.getName(),
                (long) authorEntity.getBooks().size(),
                "Status",
                weatherDTO
        );
    }

    @Transactional
    public SuccessCreated addBookForAuthor(
            Long authorId,
            Long bookId
    ) {
        utilsMethods.isExistsByAuthorAndBookId(authorId, bookId);

        var authorEntity = authorRepository.findById(authorId).orElseThrow(
                () -> new AuthorNotFoundException("Author with id = %s not found".formatted(authorId))
        );
        var bookEntity = bookRepository.findById(bookId).orElseThrow(
                () -> new BookNotFoundException("Book with id = %s not found".formatted(bookId))
        );

        var books = authorEntity.getBooks();
        books.add(authorBookMapper.toEntity(authorEntity, bookEntity));

        return successCreatedMapper.toSuccessCreated(
                String.valueOf(books.size()),
                ConstantMessages.BOOK_SUCCESS_ADDED.getDescription(),
                LocalDateTime.now().toString()
        );
    }
}
