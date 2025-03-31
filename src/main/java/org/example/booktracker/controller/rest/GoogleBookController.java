package org.example.booktracker.controller.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.booktracker.domain.book.GoogleBookDto;
import org.example.booktracker.service.GoogleBookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/google-books")
@RequiredArgsConstructor
public class GoogleBookController {
    private final GoogleBookService googleBookService;

    @GetMapping
    public Mono<ResponseEntity<List<GoogleBookDto>>> getBooks(
            @RequestParam String query
    ) {
        return googleBookService.fetchBooks(query)
                .map(ResponseEntity::ok)
                .onErrorResume(e -> Mono.just(ResponseEntity.notFound().build()));
    }
}
