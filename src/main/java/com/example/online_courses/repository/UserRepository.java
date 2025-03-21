package com.example.online_courses.repository;

import com.example.online_courses.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    User findByEmail(String email); // Tìm user theo email
}