package org.example.booktracker.controller.view;


import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import org.example.booktracker.service.AuthorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/authors")
@RequiredArgsConstructor
public class AuthorViewController {
    private final AuthorService authorService;

    @GetMapping
    public String authors(Model model) {
        return "authors";
    }

    @GetMapping("/{id}")
    public String getAuthorProfile(
            @PathVariable("id") Long id,
            Model model
    ){
        var authorProfile = authorService.findAuthorProfile(id);
        if (authorProfile == null) {
            throw new EntityNotFoundException("Author profile not found for ID: " + id);
        }
        model.addAttribute("author", authorProfile);
        return "author-profile";
    }
}
