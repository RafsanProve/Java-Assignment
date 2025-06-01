package com.example.java_assignment.service;

import com.example.java_assignment.dto.JwtResponse;
import com.example.java_assignment.model.Admin;
import com.example.java_assignment.model.AppUser;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AdminService {
    ResponseEntity<Admin> loadAdminByUsername(String username);
    ResponseEntity<JwtResponse> validateLogin(String credential, String password);
    ResponseEntity<List<AppUser>> getAllUsers();
    ResponseEntity<JwtResponse> saveAdmin(String email, String password);
}
