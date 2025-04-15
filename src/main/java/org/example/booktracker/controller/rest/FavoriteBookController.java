package org.example.booktracker.controller.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.booktracker.domain.book.MainBookInfoDto;
import org.example.booktracker.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.logging.Logger;

@Slf4j
@RestController
@RequestMapping("/api/v1/favorite-book")
@RequiredArgsConstructor
public class FavoriteBookController {
    final Logger logger = Logger.getLogger(FavoriteBookController.class.getName());
    private final BookService bookService;

    @GetMapping("/{id}")
    public ResponseEntity<List<MainBookInfoDto>> getFavoriteBooks(
            @PathVariable("id") Long userId
    ) {
        logger.info(() -> "Get request for user favorite books with id = %s".formatted(userId));
        return ResponseEntity.ok().body(bookService.findFavoriteUserBooks(userId));
    }
}
