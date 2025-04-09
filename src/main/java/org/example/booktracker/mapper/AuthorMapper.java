package org.example.booktracker.mapper;

import org.example.booktracker.domain.author.AuthorCreateDto;
import org.example.booktracker.domain.author.AuthorEntity;
import org.example.booktracker.domain.author.BestAuthorDto;
import org.example.booktracker.domain.authorBook.AuthorBookEntity;
import org.example.booktracker.domain.city.CityEntity;
import org.example.booktracker.domain.user.UserStatus;
import org.example.booktracker.utils.CityRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.TargetType;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface AuthorMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "authorCreateDto.name")
    @Mapping(target = "password", source = "encodedPassword")
    @Mapping(target = "books", expression = "java(initializeBookList(authorEntity))")
    @Mapping(target = "status", expression = "java(setUserStatus())")
    @Mapping(target = "city", source = "cityEntity")
    AuthorEntity toEntity(AuthorCreateDto authorCreateDto, String encodedPassword, CityEntity cityEntity);

    default List<AuthorBookEntity> initializeBookList(@TargetType AuthorEntity authorEntity) {
        if (authorEntity.getBooks() == null) authorEntity.setBooks(new ArrayList<>());
        return authorEntity.getBooks();
    }

    default String setUserStatus() {
        return UserStatus.ACTIVE.getDescription();
    }


    BestAuthorDto toDto(AuthorEntity authorEntity, Double rating, List<String> bestBooks);
}
