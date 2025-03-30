package org.example.booktracker.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.example.booktracker.mapper.QuotesMapper;
import org.example.booktracker.repository.DailyQuotesRepository;
import org.example.booktracker.repository.QuotesRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class DailyQuoteService {
    final Logger logger = Logger.getLogger(DailyQuoteService.class.getName());
    private final DailyQuotesRepository dailyQuotesRepository;
    private final QuotesRepository quotesRepository;
    private final QuotesMapper quotesMapper;

    @Scheduled(cron = "0 0 * * * *")
    @Transactional
    public void scheduleFixedDelayTask() {
        logger.info(() -> "Updating daily quotes!");
        updateDailyQuotes();
    }

    protected void updateDailyQuotes() {
        if (!dailyQuotesRepository.findAll().isEmpty()) dailyQuotesRepository.deleteAll();

        var shuffledQuotes = quotesRepository.findAll()
                .stream()
                .collect(Collectors.collectingAndThen(
                        Collectors.toList(),
                        collected -> {
                            Collections.shuffle(collected);
                            return collected;
                        }
                ));

        shuffledQuotes.stream()
                .limit(10)
                .forEach(quotesEntity ->
                        dailyQuotesRepository.save(quotesMapper.toDailyEntity(quotesEntity))
                );

    }
}
