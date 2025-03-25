package com.example.online_courses.controller;

import com.example.online_courses.entity.Course;
import com.example.online_courses.entity.Lesson;
import com.example.online_courses.entity.Purchase;
import com.example.online_courses.repository.CourseRepository;
import com.example.online_courses.repository.LessonRepository;
import com.example.online_courses.repository.PurchaseRepository;
import com.example.online_courses.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private PurchaseRepository purchaseRepository;

    @GetMapping // Ánh xạ tới "/courses"
    public String listCourses(Model model) {
        List<Course> courses = courseService.getAllCourses();
        model.addAttribute("courses", courses);
        return "courses"; // Trả về template courses.html
    }

    @GetMapping("/{courseId}") // Ánh xạ tới "/courses/{courseId}"
    public String courseDetail(@PathVariable UUID courseId, Model model) {
        Course course = courseService.getCourseById(courseId);
        model.addAttribute("course", course);
        return "course-detail"; // Trả về template course-detail.html
    }

    @GetMapping("/{id}/learn") // Ánh xạ tới "/courses/{id}/learn"
    @PreAuthorize("isAuthenticated()")
    public String learnCourse(@PathVariable("id") UUID id, Model model) {
        Course course = courseRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid course ID"));
        
        boolean hasAccess = course.getPrice().equals(BigDecimal.ZERO) || purchaseRepository.existsByUserIdAndCourseIdAndStatus(
            UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getName()), 
            id, 
            "completed"
        );
        if (!hasAccess) {
            return "redirect:/courses/" + id + "/payment";
        }

        List<Lesson> lessons = lessonRepository.findByCourseIdOrderByOrderNumberAsc(id);
        model.addAttribute("course", course);
        model.addAttribute("lessons", lessons);
        return "course-learn";
    }

    @GetMapping("/{id}/payment") // Ánh xạ tới "/courses/{id}/payment"
    @PreAuthorize("isAuthenticated()")
    public String getPaymentPage(@PathVariable("id") UUID id, Model model) {
        Course course = courseRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid course ID"));
        model.addAttribute("course", course);
        return "course-payment";
    }

    @PostMapping("/{id}/purchase") // Ánh xạ tới "/courses/{id}/purchase"
    @PreAuthorize("isAuthenticated()")
    public String processPurchase(@PathVariable("id") UUID id, @RequestParam("paymentMethod") String paymentMethod, Model model) {
        Course course = courseRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid course ID"));

        Purchase purchase = new Purchase();
        purchase.setUserId(UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getName()));
        purchase.setCourseId(id);
        purchase.setAmount(course.getPrice());
        purchase.setPaymentMethod(paymentMethod);
        purchase.setStatus("completed");
        purchase.setTransactionId("TRANS-" + UUID.randomUUID().toString());
        purchaseRepository.save(purchase);

        return "redirect:/courses/" + id + "/learn";
    }
}