package io.hexlet.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller("/")
public class MainController {
    @GetMapping
    RedirectView redirect() {
        return new RedirectView("catalog");
    }

    @GetMapping("catalog")
    String index() {
        return "index.html";
    }
}
