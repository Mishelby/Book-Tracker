package org.example.booktracker.mapper;

import org.example.booktracker.domain.quotes.DailyQuoteEntity;
import org.example.booktracker.domain.quotes.DailyQuotesDto;
import org.example.booktracker.domain.quotes.QuotesEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface QuotesMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "title", source = "quotesEntity.category")
    @Mapping(target = "author", source = "quotesEntity.author")
    @Mapping(target = "description", source = "quotesEntity.text")
    DailyQuoteEntity toDailyEntity(QuotesEntity quotesEntity);


    @Mapping(target = "title", source = "dailyQuoteEntity.title")
    @Mapping(target = "author", source = "dailyQuoteEntity.author")
    @Mapping(target = "description", source = "dailyQuoteEntity.description")
    DailyQuotesDto toDto(DailyQuoteEntity dailyQuoteEntity);
}
