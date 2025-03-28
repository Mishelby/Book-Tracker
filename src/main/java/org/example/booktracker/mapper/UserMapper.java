package org.example.booktracker.mapper;

import org.example.booktracker.domain.user.UserCreateDto;
import org.example.booktracker.domain.user.UserEntity;
import org.example.booktracker.domain.user.UserProfileInfoDto;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "email", source = "user.email")
    @Mapping(target = "password", source = "encodedPassword")
    UserEntity toEntity(
            UserCreateDto user,
            String encodedPassword
    );

    @Mapping(target = "userName", source = "userEntity.username")
    @Mapping(target = "email", source = "userEntity.email")
    @Mapping(target = "status", expression = "java(getUserStatus(userEntity))")
    UserProfileInfoDto toDto(UserEntity userEntity);

    default String getUserStatus(UserEntity userEntity) {
        return "Нет текущего статуса!";
    }
}
