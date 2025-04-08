package org.example.booktracker.controller.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.booktracker.domain.authorBook.AuthorBookDto;
import org.example.booktracker.domain.book.BookCreateDto;
import org.example.booktracker.domain.book.MainBookInfoDto;
import org.example.booktracker.service.BookService;
import org.example.booktracker.utils.SuccessCreated;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@Slf4j
@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
public class BookController {
    final Logger logger = Logger.getLogger(BookController.class.getName());
    private final BookService bookService;

    @GetMapping
    public ResponseEntity<List<MainBookInfoDto>> getAllBooks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        logger.info(() -> "Get request for getting all books %s");
        return ResponseEntity.ok().body(bookService.findAll(page, size));
    }

    @PostMapping
    public ResponseEntity<SuccessCreated> addBook(
            @RequestBody BookCreateDto bookDto
    ) {
        logger.info(() -> "Post request for creating new bookDto = %s".formatted(bookDto));
        return ResponseEntity.ok().body(bookService.saveBook(bookDto));
    }

    @GetMapping("/author/{id}")
    public ResponseEntity<List<AuthorBookDto>> getAuthorBook(
            @PathVariable("id") Long id
    ) {
        logger.info(() -> "Get request for author books with id = %s".formatted(id));
        return ResponseEntity.ok().body(bookService.getAuthorBooks(id));
    }
}
