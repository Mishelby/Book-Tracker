package org.example.booktracker.mapper;

import org.example.booktracker.domain.Author.AuthorEntity;
import org.example.booktracker.domain.AuthorBook.AuthorBookEntity;
import org.example.booktracker.domain.Book.BookEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AuthorBookMapper {

    @Mapping(target = "author", source = "authorEntity")
    @Mapping(target = "book", source = "bookEntity")
    AuthorBookEntity toEntity(AuthorEntity authorEntity, BookEntity bookEntity);
}
