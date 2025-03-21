package com.example.online_courses.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "home"; // Trả về home.html
    }

    @GetMapping("/courses")
    public String courses() {
        return "courses"; // Trả về courses.html (nếu có)
    }

    @GetMapping("/news")
    public String news() {
        return "news"; // Trả về news.html (nếu có)
    }

    @GetMapping("/login")
    public String login() {
        return "login"; // Trả về login.html
    }
}