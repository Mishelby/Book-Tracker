package org.example.booktracker.controller.rest;

import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.example.booktracker.domain.author.AuthorProfileDto;
import org.example.booktracker.domain.comment.LastNCommentsDto;
import org.example.booktracker.service.AuthorService;

import java.util.List;
import java.util.logging.Logger;

import org.example.booktracker.serviceClient.CommentClientService;
import org.example.booktracker.utils.SuccessCreated;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/authors")
@RequiredArgsConstructor
public class AuthorController {
    final Logger logger = Logger.getLogger(AuthorController.class.getName());
    private final AuthorService authorService;
    private final CommentClientService commentClientService;


    @GetMapping("/{id}")
    public ResponseEntity<AuthorProfileDto> getAuthorProfile(
            @PathVariable("id") Long id
    ) {
        logger.info(() -> "Get request for author profile with id = %s".formatted(id));
        return ResponseEntity.ok().body(authorService.findAuthorProfile(id));
    }

    @GetMapping("/{id}/comments")
    public ResponseEntity<LastNCommentsDto> getComment(
            @PathVariable("id") Long userId,
            @RequestParam Long count
    ) {
        logger.info(() -> "Get request for last %s comment with id = %s".formatted(userId, count));
        return ResponseEntity.ok().body(commentClientService.getNLastComments(userId, count));
    }

    @PostMapping("/{id}/{book_id}")
    public ResponseEntity<SuccessCreated> addBookForAuthor(
            @PathVariable("id") Long authorId,
            @PathVariable("book_id") Long bookId
    ) {
        logger.info(() -> "Post request for adding book with id = %s for author with id = %s "
                .formatted(authorId, bookId));
        return ResponseEntity.ok().body(authorService.addBookForAuthor(authorId, bookId));
    }
}
