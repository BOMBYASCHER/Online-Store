package io.hexlet.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller("/")
public class MainController {
    @GetMapping
    RedirectView redirect() {
        return new RedirectView("catalog");
    }

    @GetMapping("catalog")
    ModelAndView index() {
        return new ModelAndView("index.html");
    }

    @GetMapping("cart")
    ModelAndView cart() {
        return new ModelAndView("cart.html");
    }

    @GetMapping("login")
    ModelAndView login() {
        return new ModelAndView("login.html");
    }

    @GetMapping("registry")
    ModelAndView registry() {
        return new ModelAndView("registry.html");
    }

    @GetMapping("product")
    ModelAndView product() {
        return new ModelAndView("product.html");
    }

    @GetMapping("about")
    ModelAndView about() {
        return new ModelAndView("about_us.html");
    }
}
