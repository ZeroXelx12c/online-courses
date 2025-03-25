package com.example.online_courses.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.online_courses.entity.Purchase;

import java.util.UUID;

public interface PurchaseRepository extends JpaRepository<Purchase, UUID> {
    boolean existsByUserIdAndCourseIdAndStatus(UUID userId, UUID courseId, String status);
}