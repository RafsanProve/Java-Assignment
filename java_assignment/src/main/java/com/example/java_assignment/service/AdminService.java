package com.example.java_assignment.service;

import com.example.java_assignment.model.Admin;
import org.springframework.http.ResponseEntity;

public interface AdminService {
    ResponseEntity<Admin> loadAdminByUsername(String username);
}
