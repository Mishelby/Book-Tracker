package org.example.booktracker.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.booktracker.domain.author.AuthorEntity;
import org.example.booktracker.domain.author.BestAuthorDto;
import org.example.booktracker.mapper.AuthorMapper;
import org.example.booktracker.repository.AuthorRatingRepository;
import org.example.booktracker.repository.AuthorRepository;
import org.example.booktracker.repository.BookRepository;
import org.example.booktracker.repository.UserRepository;
import org.example.booktracker.utils.ConstantMessages;
import org.example.booktracker.utils.StartPageDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

@Slf4j
@Service
@RequiredArgsConstructor
public class StartPageService {
    // Logger
    final Logger logger = Logger.getLogger(StartPageService.class.getName());

    // Repositories
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final UserRepository userRepository;
    private final AuthorRatingRepository authorRatingRepository;
    private final AuthorMapper authorMapper;


    @Transactional(readOnly = true)
    public StartPageDto getStartPage() {
        logger.info(() -> "Collecting information about starting page");

        return new StartPageDto(
                ConstantMessages.START_MESSAGE.getDescription(),
                findBestAuthors(),
                userRepository.countAllUsers().orElse(0L),
                authorRepository.countAllAuthors().orElse(0L),
                bookRepository.countAllBooks().orElse(0L),
                "This is amazing day!"
        );
    }

    private List<BestAuthorDto> findBestAuthors() {
        var bestAuthorsByRating = authorRepository.findBestAuthorsByRating(3.1);
        if (bestAuthorsByRating.isEmpty()) return Collections.emptyList();

        return bestAuthorsByRating.stream()
                .map(author -> {
                    var authorRating = authorRatingRepository.findRatingByAuthorId(author.getId()).orElse(0.0);
                    var bestBooks = bookRepository.findBestBooks(author.getId()).orElse(new ArrayList<>());
                    return authorMapper.toDto(author, authorRating, bestBooks);
                })
                .toList();
    }
}
