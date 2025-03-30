package org.example.booktracker.repository;

import org.example.booktracker.domain.quotes.QuotesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuotesRepository extends JpaRepository<QuotesEntity, Long> {
}
