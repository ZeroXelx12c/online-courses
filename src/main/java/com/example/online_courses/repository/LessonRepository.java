package com.example.online_courses.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.online_courses.entity.Lesson;

import java.util.List;
import java.util.UUID;

public interface LessonRepository extends JpaRepository<Lesson, UUID> {
    List<Lesson> findByCourseIdOrderByOrderNumberAsc(UUID courseId);
}