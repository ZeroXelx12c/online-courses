package com.example.online_courses.controller;

import com.example.online_courses.entity.User;
import com.example.online_courses.service.CourseService;
import com.example.online_courses.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private CourseService courseService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("userCount", userService.getAllUsers().size());
        model.addAttribute("courseCount", courseService.getAllCourses().size());
        model.addAttribute("revenue", "0");
        return "admin-dashboard";
    }

    @GetMapping("/users")
    public String listUsers(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "admin-users";
    }

    @GetMapping("/users/edit/{id}")
    public String editUserForm(@PathVariable UUID id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "admin-user-edit"; // Template form sửa
    }

    @PostMapping("/users/edit/{id}")
    public String updateUser(@PathVariable UUID id,
                             @RequestParam("email") String email,
                             @RequestParam("fullName") String fullName,
                             @RequestParam("role") String role) {
        userService.updateUser(id, email, fullName, role);
        return "redirect:/admin/users?success=edit"; // Chuyển hướng với thông báo
    }

    @PostMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
        return "redirect:/admin/users?success=delete"; // Chuyển hướng với thông báo
    }

    @GetMapping("/users/add")
    public String addUserForm(Model model) {
        model.addAttribute("user", new User()); // User rỗng cho form
        return "admin-user-add"; // Template form thêm
    }

    @PostMapping("/users/add")
    public String addUser(@RequestParam("email") String email,
                          @RequestParam("password") String password,
                          @RequestParam("fullName") String fullName,
                          @RequestParam("role") String role) {
        userService.addUser(email, password, fullName, role);
        return "redirect:/admin/users?success=add"; // Chuyển hướng với thông báo
    }
}