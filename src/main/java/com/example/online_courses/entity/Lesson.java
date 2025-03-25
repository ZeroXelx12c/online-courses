package com.example.online_courses.entity;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "Lessons")
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "lesson_id")
    private UUID lessonId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "video_url", nullable = false)
    private String videoUrl;

    @Column(name = "order_number", nullable = false)
    private int orderNumber;

    @Column(name = "course_id", nullable = false)
    private UUID courseId;

    // Getters và Setters
    public UUID getLessonId() { return lessonId; }
    public void setLessonId(UUID lessonId) { this.lessonId = lessonId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getVideoUrl() { return videoUrl; }
    public void setVideoUrl(String videoUrl) { this.videoUrl = videoUrl; }
    public int getOrderNumber() { return orderNumber; }
    public void setOrderNumber(int orderNumber) { this.orderNumber = orderNumber; }
    public UUID getCourseId() { return courseId; }
    public void setCourseId(UUID courseId) { this.courseId = courseId; }
}