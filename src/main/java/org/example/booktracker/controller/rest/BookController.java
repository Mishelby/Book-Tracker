package org.example.booktracker.controller.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.booktracker.domain.Book.BookCreateDto;
import org.example.booktracker.service.BookService;
import org.example.booktracker.utils.SuccessCreated;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@Slf4j
@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
public class BookController {
    final Logger logger = Logger.getLogger(BookController.class.getName());
    private final BookService bookService;

    @PostMapping
    public ResponseEntity<SuccessCreated> addBook(
            @RequestBody BookCreateDto bookDto
    ) {
        logger.info(() -> "Post request for creating new bookDto = %s".formatted(bookDto));
        return ResponseEntity.ok().body(bookService.saveBook(bookDto));
    }
}
