package org.example.booktracker.controller.view;

import lombok.RequiredArgsConstructor;
import org.example.booktracker.domain.book.GoogleBookDto;
import org.example.booktracker.service.GoogleBookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.List;


@Controller
@RequestMapping("/search-book")
@RequiredArgsConstructor
public class BookSearchViewController {
    private final GoogleBookService googleBookService;

    // Constant
    private static final String RESULT = "book-search";

    @GetMapping
    public DeferredResult<String> findBook(@RequestParam(required = false) String query, Model model) {
        DeferredResult<String> deferredResult = new DeferredResult<>();

        if (query == null || query.trim().isEmpty()) {
            deferredResult.setResult(RESULT);
            return deferredResult;
        }

        googleBookService.fetchBooks(query)
                .subscribe(
                        books -> {
                            model.addAttribute("books", books);
                            model.addAttribute("query", query);
                            deferredResult.setResult(RESULT);
                        },
                        error -> {
                            model.addAttribute("error", "Ошибка загрузки книг: " + error.getMessage());
                            deferredResult.setResult(RESULT);
                        }
                );

        return deferredResult;
    }

}
