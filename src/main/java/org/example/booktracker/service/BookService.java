package org.example.booktracker.service;

import lombok.RequiredArgsConstructor;
import org.example.booktracker.domain.authorBook.AuthorBookDto;
import org.example.booktracker.domain.book.BookCreateDto;
import org.example.booktracker.domain.book.BookGenre;
import org.example.booktracker.mapper.SuccessCreatedMapper;
import org.example.booktracker.mapper.BookMapper;
import org.example.booktracker.repository.BookRepository;
import org.example.booktracker.utils.ConstantMessages;
import org.example.booktracker.utils.SuccessCreated;
import org.example.booktracker.utils.UtilsMethods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.logging.Logger;


@Service
@RequiredArgsConstructor
public class BookService {
    final Logger logger = Logger.getLogger(BookService.class.getName());
    private final BookRepository bookRepository;
    private final GenreInitializeService genreInitializeService;

    // Mappers
    private final SuccessCreatedMapper successCreatedMapper;
    private final BookMapper bookMapper;

    // Utils
    private final UtilsMethods utilsMethods;
    private final UUID uuid;

    @Transactional
    public SuccessCreated saveBook(
            BookCreateDto bookDto
    ) {
        utilsMethods.isExistsByNameAndDescription(
                bookDto.name(),
                bookDto.description()
        );
        var initializedGenres = genreInitializeService.initialize();

        var savedBook = bookRepository.save(
                bookMapper.toEntity(
                        bookDto,
                        uuid.toString(),
                        initializedGenres.get(BookGenre.getBookGenre(bookDto.genre()))
                )
        );

        return successCreatedMapper.toSuccessCreated(
                savedBook.toString(),
                ConstantMessages.BOOK_SUCCESS_CREATED.getDescription(),
                LocalDateTime.now().toString()
        );
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "authorBooks", key = "#id")
    public List<AuthorBookDto> getAuthorBooks(
            Long id
    ) {
        logger.info(() -> "getAuthorBooks called");
        var byAuthorId = bookRepository.findBooksByAuthorId(id);
        if (byAuthorId.isEmpty()) return Collections.emptyList();

        return byAuthorId.stream()
                .map(authorBooks ->
                        bookMapper.toDto(authorBooks.getBook(), (long) byAuthorId.size()))
                .toList();
    }

}
