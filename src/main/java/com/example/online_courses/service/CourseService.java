package com.example.online_courses.service;

import com.example.online_courses.entity.Course;
import com.example.online_courses.entity.User;
import com.example.online_courses.repository.CourseRepository;
import com.example.online_courses.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserRepository userRepository;

    public Course createCourse(String title, String description, BigDecimal price, UUID instructorId, String thumbnailUrl) {
        // Tìm instructor từ userRepository
        User instructor = userRepository.findById(instructorId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy giảng viên với ID: " + instructorId));

        Course course = new Course();
        course.setTitle(title);
        course.setDescription(description);
        course.setPrice(price != null ? price : BigDecimal.ZERO);
        course.setThumbnailUrl(thumbnailUrl);
        return courseRepository.save(course);
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Course getCourseById(UUID courseId) {
        return courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy khóa học với ID: " + courseId));
    }
}