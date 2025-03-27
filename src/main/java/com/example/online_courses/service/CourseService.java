package com.example.online_courses.service;

import com.example.online_courses.entity.Course;
import com.example.online_courses.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    // Lấy tất cả khóa học (không phân trang - dùng trong dashboard hoặc trang công khai)
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    // Lấy khóa học theo ID
    public Course getCourseById(UUID id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy khóa học với ID: " + id));
    }

    // Tìm kiếm khóa học với phân trang (dùng trong admin)
    public Page<Course> searchCourses(String search, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        if (search == null || search.trim().isEmpty()) {
            return courseRepository.findAll(pageable);
        }
        return courseRepository.findByTitleContainingIgnoreCase(search, pageable);
    }

    // Lưu khóa học (thêm mới hoặc cập nhật)
    public void saveCourse(Course course) {
        courseRepository.save(course);
    }

    // Xóa khóa học theo ID
    public void deleteCourse(UUID id) {
        Course course = getCourseById(id);
        courseRepository.delete(course);
    }
}