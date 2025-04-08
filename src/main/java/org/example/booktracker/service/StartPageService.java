package org.example.booktracker.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.booktracker.domain.author.AuthorEntity;
import org.example.booktracker.domain.author.BestAuthorDto;
import org.example.booktracker.domain.quotes.DailyQuoteEntity;
import org.example.booktracker.domain.quotes.DailyQuotesDto;
import org.example.booktracker.domain.quotes.QuotesEntity;
import org.example.booktracker.mapper.AuthorMapper;
import org.example.booktracker.mapper.QuotesMapper;
import org.example.booktracker.repository.*;
import org.example.booktracker.utils.ConstantMessages;
import org.example.booktracker.utils.DefaultValues;
import org.example.booktracker.utils.StartPageDto;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
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
    private final DailyQuotesRepository dailyQuotesRepository;
    private final QuotesRepository quotesRepository;

    // Utils
    private final Random random = new Random();
    private final QuotesMapper quotesMapper;


    @Transactional(readOnly = true)
    @Cacheable(value = "startPage")
    public StartPageDto getStartPage() {
        logger.info(() -> "Collecting information about starting page");

        StartPageDto startPageDto = new StartPageDto(
                ConstantMessages.START_MESSAGE.getDescription(),
                findBestAuthors(),
                userRepository.countAllUsers().orElse(0L),
                authorRepository.countAllAuthors().orElse(0L),
                bookRepository.countAllBooks().orElse(0L),
                getDailyQuote(),
                getDailyQuotesList()
        );

        logger.info(() -> "Finished collecting information about starting page %s". formatted(startPageDto));
        return startPageDto;
    }

    private List<BestAuthorDto> findBestAuthors() {
        var bestAuthorsByRating = authorRepository.findBestAuthorsByRating(DefaultValues.getBestRating());
        if (bestAuthorsByRating.isEmpty()) return Collections.emptyList();

        return bestAuthorsByRating.stream()
                .map(author -> {
                    var authorRating = authorRatingRepository.findRatingByAuthorId(author.getId()).orElse(0.0);
                    var bestBooks = bookRepository.findBestBooks(author.getId()).orElse(new ArrayList<>());
                    return authorMapper.toDto(author, authorRating, bestBooks);
                }).toList();
    }

    private String getDailyQuote() {
        var allQuotes = quotesRepository.findAll();
        return allQuotes.get(random.nextInt(0, allQuotes.size() - 1)).getText();
    }

    private List<DailyQuotesDto> getDailyQuotesList() {
        var allDailyQuotes = dailyQuotesRepository.findAll();
        if(allDailyQuotes.isEmpty()) return Collections.emptyList();

        return allDailyQuotes.stream()
                .map(quotesMapper::toDto)
                .toList();
    }
}
