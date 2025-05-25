package com.example.java_assignment.repository;

import com.example.java_assignment.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
    Optional<Admin> findByEmail(String email); // For login
    Optional<Admin> findByUsername(String username);
}
