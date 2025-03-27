package org.example.booktracker.utils;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.io.Serializable;

@EqualsAndHashCode
@Getter
public class AuthorBookId implements Serializable {
    private Long author;
    private Long book;

    public AuthorBookId(Long author, Long book) {
        this.author = author;
        this.book = book;
    }

    public AuthorBookId() {}
}
