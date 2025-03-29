package org.example.booktracker.mapper;

import org.example.booktracker.domain.authorBook.AuthorBookDto;
import org.example.booktracker.domain.authorBook.AuthorBookEntity;
import org.example.booktracker.domain.book.BookCreateDto;
import org.example.booktracker.domain.book.BookEntity;
import org.example.booktracker.domain.bookGenre.BookGenreEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.TargetType;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface BookMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "bookDto.name")
    @Mapping(target = "description", source = "bookDto.description")
    @Mapping(target = "article", source = "article")
    @Mapping(target = "authors", expression = "java(initializeAuthorList(bookEntity))")
    @Mapping(target = "genre", source = "genre")
    BookEntity toEntity(BookCreateDto bookDto, String article, BookGenreEntity genre);

    default List<AuthorBookEntity> initializeAuthorList(@TargetType BookEntity bookEntity) {
        if (bookEntity.getAuthors() == null) bookEntity.setAuthors(new ArrayList<>());
        return bookEntity.getAuthors();
    }

    @Mapping(target = "countOfBooks", source = "countOfBooks")
    @Mapping(target = "name", source = "bookEntity.name")
    @Mapping(target = "article", source = "bookEntity.article")
    AuthorBookDto toDto(BookEntity bookEntity, Long countOfBooks);
}
