package org.example.booktracker.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.booktracker.domain.book.BookGenre;
import org.example.booktracker.domain.bookGenre.BookGenreEntity;
import org.example.booktracker.repository.GenreRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class GenreInitializeService {
    private final GenreRepository genreRepository;

    @Transactional
    public ConcurrentMap<BookGenre, BookGenreEntity> initialize() {
        // Get all genres
        var genresEnum = BookGenre.values();

        // Convert to ConcurrentHashMap
        var allGenres = genreRepository.findAll()
                .stream()
                .collect(Collectors.toConcurrentMap(
                        BookGenreEntity::getBookGenre,
                        Function.identity(),
                        (oldValue, newValue) -> newValue,
                        ConcurrentHashMap::new
                ));

        var missingGenres = Arrays.stream(genresEnum)
                .filter(genre -> !allGenres.containsKey(genre))
                .collect(Collectors.toSet());

        if (!missingGenres.isEmpty()) {
            missingGenres.forEach(genre -> {
                var bookGenreEntity = new BookGenreEntity(genre);
                genreRepository.save(bookGenreEntity);
                allGenres.put(genre, bookGenreEntity);
            });
        }

        return allGenres;
    }
}
