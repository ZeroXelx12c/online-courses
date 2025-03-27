package com.example.online_courses.controller;

import com.example.online_courses.entity.Course;
import com.example.online_courses.entity.Lesson;
import com.example.online_courses.entity.User;
import com.example.online_courses.service.CourseService;
import com.example.online_courses.service.LessonService;
import com.example.online_courses.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private LessonService lessonService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("userCount", userService.getAllUsers(0, 10).getTotalElements());
        model.addAttribute("courseCount", courseService.getAllCourses().size());
        model.addAttribute("revenue", "0");
        return "admin-dashboard";
    }

    // Quản lý người dùng
    @GetMapping("/users")
    public String listUsers(@RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "5") int size,
            @RequestParam(value = "search", required = false) String search,
            Model model) {
        Page<User> userPage = userService.searchUsers(search, page, size);
        model.addAttribute("users", userPage.getContent());
        model.addAttribute("currentPage", userPage.getNumber());
        model.addAttribute("totalPages", userPage.getTotalPages());
        model.addAttribute("totalItems", userPage.getTotalElements());
        model.addAttribute("pageSize", size);
        model.addAttribute("search", search);
        return "admin-users";
    }

    @GetMapping("/users/edit/{id}")
    public String editUserForm(@PathVariable UUID id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "admin-user-edit";
    }

    @PostMapping("/users/edit/{id}")
    public String updateUser(@PathVariable UUID id,
            @RequestParam("email") String email,
            @RequestParam("fullName") String fullName,
            @RequestParam("role") String role) {
        userService.updateUser(id, email, fullName, role);
        return "redirect:/admin/users?success=edit";
    }

    @PostMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
        return "redirect:/admin/users?success=delete";
    }

    @GetMapping("/users/add")
    public String addUserForm(Model model) {
        model.addAttribute("user", new User());
        return "admin-user-add";
    }

    @PostMapping("/users/add")
    public String addUser(@RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("fullName") String fullName,
            @RequestParam("role") String role) {
        userService.addUser(email, password, fullName, role);
        return "redirect:/admin/users?success=add";
    }

    // Quản lý khóa học
    @GetMapping("/courses")
    public String listCourses(@RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "5") int size,
            @RequestParam(value = "search", required = false) String search,
            Model model) {
        Page<Course> coursePage = courseService.searchCourses(search, page, size);
        model.addAttribute("courses", coursePage.getContent());
        model.addAttribute("currentPage", coursePage.getNumber());
        model.addAttribute("totalPages", coursePage.getTotalPages());
        model.addAttribute("totalItems", coursePage.getTotalElements());
        model.addAttribute("pageSize", size);
        model.addAttribute("search", search);
        return "admin-courses";
    }

    @GetMapping("/courses/add")
    public String addCourseForm(Model model) {
        model.addAttribute("course", new Course());
        return "admin-course-add";
    }

    @PostMapping("/courses/add")
    public String addCourse(@RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("price") BigDecimal price,
            @RequestParam("thumbnailUrl") String thumbnailUrl,
            @RequestParam(value = "rating", required = false) Double rating) {
        Course course = new Course();
        course.setTitle(title);
        course.setDescription(description);
        course.setPrice(price);
        course.setThumbnailUrl(thumbnailUrl);
        course.setRating(rating != null ? rating : 0.0);
        courseService.saveCourse(course);
        return "redirect:/admin/courses?success=add";
    }

    @GetMapping("/courses/edit/{id}")
    public String editCourseForm(@PathVariable UUID id, Model model) {
        Course course = courseService.getCourseById(id);
        model.addAttribute("course", course);
        return "admin-course-edit";
    }

    @PostMapping("/courses/edit/{id}")
    public String updateCourse(@PathVariable UUID id,
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("price") BigDecimal price,
            @RequestParam("thumbnailUrl") String thumbnailUrl,
            @RequestParam(value = "rating", required = false) Double rating) {
        Course course = courseService.getCourseById(id);
        course.setTitle(title);
        course.setDescription(description);
        course.setPrice(price);
        course.setThumbnailUrl(thumbnailUrl);
        course.setRating(rating != null ? rating : course.getRating());
        courseService.saveCourse(course);
        return "redirect:/admin/courses?success=edit";
    }

    @PostMapping("/courses/delete/{id}")
    public String deleteCourse(@PathVariable UUID id) {
        courseService.deleteCourse(id);
        return "redirect:/admin/courses?success=delete";
    }

    // Quản lý tất cả bài học (tổng quát)
    @GetMapping("/courses-details")
    public String listAllLessons(@RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "5") int size,
            @RequestParam(value = "search", required = false) String search,
            Model model) {
        Page<Lesson> lessonPage = lessonService.searchLessons(null, search, page, size);
        model.addAttribute("lessons", lessonPage.getContent());
        model.addAttribute("currentPage", lessonPage.getNumber());
        model.addAttribute("totalPages", lessonPage.getTotalPages());
        model.addAttribute("totalItems", lessonPage.getTotalElements());
        model.addAttribute("pageSize", size);
        model.addAttribute("search", search);
        model.addAttribute("courses", courseService.getAllCourses()); // Thêm danh sách khóa học
        return "admin-courses-details";
    }

    // Quản lý bài học của một khóa học cụ thể
    @GetMapping("/courses/{id}/details")
    public String listLessons(@PathVariable UUID id,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "5") int size,
            @RequestParam(value = "search", required = false) String search,
            Model model) {
        try {
            Course course = courseService.getCourseById(id);
            if (course == null) {
                throw new IllegalArgumentException("Khóa học không tồn tại");
            }
            Page<Lesson> lessonPage = lessonService.searchLessons(id, search, page, size);
            model.addAttribute("course", course);
            model.addAttribute("lessons", lessonPage.getContent());
            model.addAttribute("currentPage", lessonPage.getNumber());
            model.addAttribute("totalPages", lessonPage.getTotalPages());
            model.addAttribute("totalItems", lessonPage.getTotalElements());
            model.addAttribute("pageSize", size);
            model.addAttribute("search", search);
            return "admin-course-details";
        } catch (Exception e) {
            model.addAttribute("error", "Có lỗi xảy ra: " + e.getMessage());
            return "error"; // Giả định bạn có trang error.html, nếu không thì redirect
        }
    }

    @GetMapping("/courses/{id}/lessons/add")
    public String addLessonForm(@PathVariable UUID id, Model model) {
        Course course = courseService.getCourseById(id);
        model.addAttribute("course", course);
        model.addAttribute("lesson", new Lesson());
        return "admin-lesson-add";
    }

    @PostMapping("/courses/{id}/lessons/add")
    public String addLesson(@PathVariable UUID id,
            @RequestParam("title") String title,
            @RequestParam("videoUrl") String videoUrl,
            @RequestParam("orderIndex") Integer orderIndex) {
        Course course = courseService.getCourseById(id);
        Lesson lesson = new Lesson();
        lesson.setTitle(title);
        lesson.setVideoUrl(videoUrl);
        lesson.setOrderIndex(orderIndex);
        lesson.setCourse(course);
        lessonService.saveLesson(lesson);
        return "redirect:/admin/courses/" + id + "/details?success=add";
    }

    @GetMapping("/courses/{courseId}/lessons/edit/{lessonId}")
    public String editLessonForm(@PathVariable UUID courseId,
            @PathVariable UUID lessonId,
            Model model) {
        Course course = courseService.getCourseById(courseId);
        Lesson lesson = lessonService.getLessonById(lessonId);
        model.addAttribute("course", course);
        model.addAttribute("lesson", lesson);
        return "admin-lesson-edit";
    }

    @PostMapping("/courses/{courseId}/lessons/edit/{lessonId}")
    public String updateLesson(@PathVariable UUID courseId,
            @PathVariable UUID lessonId,
            @RequestParam("title") String title,
            @RequestParam("videoUrl") String videoUrl,
            @RequestParam("orderIndex") Integer orderIndex) {
        Lesson lesson = lessonService.getLessonById(lessonId);
        lesson.setTitle(title);
        lesson.setVideoUrl(videoUrl);
        lesson.setOrderIndex(orderIndex);
        lessonService.saveLesson(lesson);
        return "redirect:/admin/courses/" + courseId + "/details?success=edit";
    }

    @PostMapping("/courses/{courseId}/lessons/delete/{lessonId}")
    public String deleteLesson(@PathVariable UUID courseId,
            @PathVariable UUID lessonId) {
        lessonService.deleteLesson(lessonId);
        return "redirect:/admin/courses/" + courseId + "/details?success=delete";
    }

}