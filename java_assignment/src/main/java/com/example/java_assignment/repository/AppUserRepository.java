package com.example.java_assignment.repository;

import com.example.java_assignment.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByEmail(String email); // For login
    Optional<AppUser> findByUsername(String username);
    Optional<AppUser> findById(Long id);
}
