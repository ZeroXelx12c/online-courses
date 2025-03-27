package com.example.online_courses.repository;

import com.example.online_courses.entity.Lesson;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface LessonRepository extends JpaRepository<Lesson, UUID> {
    Page<Lesson> findByCourseCourseId(UUID courseId, Pageable pageable);
    Page<Lesson> findByCourseCourseIdAndTitleContainingIgnoreCase(UUID courseId, String title, Pageable pageable);
    Page<Lesson> findByTitleContainingIgnoreCase(String title, Pageable pageable);
    List<Lesson> findByCourseCourseIdOrderByOrderIndexAsc(UUID courseId);
}