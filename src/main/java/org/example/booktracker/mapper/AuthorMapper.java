package org.example.booktracker.mapper;

import org.example.booktracker.domain.Author.AuthorCreateDto;
import org.example.booktracker.domain.Author.AuthorEntity;
import org.example.booktracker.domain.AuthorBook.AuthorBookEntity;
import org.example.booktracker.domain.User.UserStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.TargetType;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface AuthorMapper {

    @Mapping(target = "name", source = "authorCreateDto.name")
    @Mapping(target = "password", source = "encodedPassword")
    @Mapping(target = "books", expression = "java(initializeBookList(authorEntity))")
    @Mapping(target = "status", expression = "java(setUserStatus())")
    AuthorEntity toEntity(AuthorCreateDto authorCreateDto, String encodedPassword);

    default List<AuthorBookEntity> initializeBookList(@TargetType AuthorEntity authorEntity) {
        if (authorEntity.getBooks() == null) authorEntity.setBooks(new ArrayList<>());
        return authorEntity.getBooks();
    }

    default String setUserStatus() {
        return UserStatus.ACTIVE.getDescription();
    }
}
