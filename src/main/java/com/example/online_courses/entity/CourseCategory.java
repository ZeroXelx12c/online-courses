package com.example.online_courses.entity;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Course_Category")
@Data
public class CourseCategory {
    @Embeddable
    public static class CourseCategoryId implements Serializable {
        private Long course;
        private Long category;

        public CourseCategoryId() {}

        public CourseCategoryId(Long course, Long category) {
            this.course = course;
            this.category = category;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            CourseCategoryId that = (CourseCategoryId) o;
            return Objects.equals(course, that.course) &&
                   Objects.equals(category, that.category);
        }

        @Override
        public int hashCode() {
            return Objects.hash(course, category);
        }
    }

    @EmbeddedId
    private CourseCategoryId id;
    
    @ManyToOne
    @MapsId("course")
    @JoinColumn(name = "course_id", referencedColumnName = "course_id", nullable = false)
    private Course course;

    @ManyToOne
    @MapsId("category")
    @JoinColumn(name = "category_id", referencedColumnName = "category_id", nullable = false)
    private Category category;
}