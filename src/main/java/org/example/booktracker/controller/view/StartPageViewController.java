package org.example.booktracker.controller.view;

import lombok.RequiredArgsConstructor;
import org.example.booktracker.service.StartPageService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/start")
@RequiredArgsConstructor
public class StartPageViewController {
    private final StartPageService startPageService;

    @GetMapping
    public String startPage(Model model) {
        var startPage = startPageService.getStartPage();
        model.addAttribute("startPage", startPage);
        return "start-page";
    }
}
