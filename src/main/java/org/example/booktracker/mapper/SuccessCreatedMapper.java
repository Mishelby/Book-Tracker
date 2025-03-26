package org.example.booktracker.mapper;

import org.example.booktracker.utils.SuccessCreated;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SuccessCreatedMapper {

    @Mapping(target = "userInfo", source = "userInfo")
    @Mapping(target = "message", source = "message")
    @Mapping(target = "created_at", source = "dateTime")
    SuccessCreated toSuccessCreated(String userInfo, String message, String dateTime);
}
