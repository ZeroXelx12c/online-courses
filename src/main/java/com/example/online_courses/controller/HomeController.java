package com.example.online_courses.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.online_courses.repository.CourseRepository;

@Controller
public class HomeController {

    @Autowired
    private CourseRepository courseRepository;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("courses", courseRepository.findAll());
        return "home"; // Trả về home.html
    }

    @GetMapping("/news")
    public String news() {
        return "news"; // Trả về news.html
    }

    @GetMapping("/login")
    public String login() {
        return "login"; // Trả về login.html
    }
}