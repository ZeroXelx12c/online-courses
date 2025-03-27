package com.example.online_courses.service;

import com.example.online_courses.entity.Lesson;
import com.example.online_courses.repository.LessonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class LessonService {

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private CourseService courseService;

    public Page<Lesson> searchLessons(UUID courseId, String search, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        if (courseId != null) {
            if (search == null || search.trim().isEmpty()) {
                return lessonRepository.findByCourseCourseId(courseId, pageable);
            }
            return lessonRepository.findByCourseCourseIdAndTitleContainingIgnoreCase(courseId, search, pageable);
        } else {
            if (search == null || search.trim().isEmpty()) {
                return lessonRepository.findAll(pageable);
            }
            return lessonRepository.findByTitleContainingIgnoreCase(search, pageable);
        }
    }

    public Lesson getLessonById(UUID lessonId) {
        return lessonRepository.findById(lessonId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy bài học với ID: " + lessonId));
    }

    public void saveLesson(Lesson lesson) {
        lessonRepository.save(lesson);
    }

    public void deleteLesson(UUID lessonId) {
        Lesson lesson = getLessonById(lessonId);
        lessonRepository.delete(lesson);
    }
}