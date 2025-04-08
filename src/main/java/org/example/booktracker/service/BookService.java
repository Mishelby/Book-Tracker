package org.example.booktracker.service;

import lombok.RequiredArgsConstructor;
import org.example.booktracker.domain.authorBook.AuthorBookDto;
import org.example.booktracker.domain.authorBook.AuthorBookEntity;
import org.example.booktracker.domain.book.BookCreateDto;
import org.example.booktracker.domain.book.BookEntity;
import org.example.booktracker.domain.book.BookGenre;
import org.example.booktracker.domain.book.MainBookInfoDto;
import org.example.booktracker.mapper.SuccessCreatedMapper;
import org.example.booktracker.mapper.BookMapper;
import org.example.booktracker.repository.BookRepository;
import org.example.booktracker.utils.ConstantMessages;
import org.example.booktracker.utils.SuccessCreated;
import org.example.booktracker.utils.UtilsMethods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    @Transactional(readOnly = true)
    @Cacheable(value = "allBooks")
    public List<MainBookInfoDto> findAll(
            int page,
            int size
    ) {
        var pageRequest = PageRequest.of(
                page,
                size,
                Sort.by(Sort.Direction.ASC, "id")
        );
        var allBooks = bookRepository.findAllBooks(pageRequest);
        if (allBooks.isEmpty()) return Collections.emptyList();

        return allBooks.stream()
                .map(book -> {
                    var authorNames = book.getAuthors().stream()
                            .map(author -> author.getAuthor().getName())
                            .toList();
                    return bookMapper.toDto(book, authorNames);
                })
                .toList();
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "startPage", allEntries = true),
            @CacheEvict(value = "allBooks", allEntries = true),
            @CacheEvict(value = "authorBooks", allEntries = true),
    })
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
                        UUID.randomUUID().toString(),
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
