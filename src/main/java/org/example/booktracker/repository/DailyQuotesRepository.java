package org.example.booktracker.repository;

import org.example.booktracker.domain.quotes.DailyQuoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DailyQuotesRepository extends JpaRepository<DailyQuoteEntity, Long> {
}
