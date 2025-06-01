package com.example.java_assignment.controller;

import com.example.java_assignment.dto.JwtResponse;
import com.example.java_assignment.dto.LoginRequest;
import com.example.java_assignment.dto.RegistrationDTO;
import com.example.java_assignment.model.AppUser;
import com.example.java_assignment.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@Tag(name = "Admin Auth")
public class AdminAuthController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/auth/login")
    @Operation(summary = "Admin login")
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody LoginRequest request) {
        return adminService.validateLogin(request.getEmail(), request.getPassword());
    }

    @GetMapping("/users")
    @Operation(summary = "List all users")
    public ResponseEntity<List<AppUser>> getAllUsers() {
        return adminService.getAllUsers();
    }
}
