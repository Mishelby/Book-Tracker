package org.example.booktracker.domain.book;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class MainBookInfoDto {
    String name;
    String description;
    String genre;
    @JsonDeserialize(as = ArrayList.class)
    List<String> authors;
    Float rating;
}
