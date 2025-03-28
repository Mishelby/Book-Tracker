package org.example.booktracker.repository;

import org.example.booktracker.domain.authorBook.AuthorBookEntity;
import org.example.booktracker.domain.book.BookCreateDto;
import org.example.booktracker.domain.book.BookEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.TargetType;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface BookMapper {

    @Mapping(target = "name", source = "bookDto.name")
    @Mapping(target = "description", source = "bookDto.description")
    @Mapping(target = "article", source = "article")
    @Mapping(target = "authors", expression = "java(initializeAuthorList(bookEntity))")
    BookEntity toEntity(BookCreateDto bookDto, String article);

    default List<AuthorBookEntity> initializeAuthorList(@TargetType BookEntity bookEntity) {
        if (bookEntity.getAuthors() == null) bookEntity.setAuthors(new ArrayList<>());
        return bookEntity.getAuthors();
    }
}
