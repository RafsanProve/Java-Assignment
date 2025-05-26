package com.example.java_assignment.controller;

import com.example.java_assignment.dto.JwtResponse;
import com.example.java_assignment.dto.LoginRequest;
import com.example.java_assignment.exception.ResourceNotFoundException;
import com.example.java_assignment.model.Admin;
import com.example.java_assignment.repository.AdminRepository;
import com.example.java_assignment.service.AdminDetailsImpl;
import com.example.java_assignment.security.JwtUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/auth")
@RequiredArgsConstructor
@Tag(name = "Admin Auth")
public class AdminAuthController {

    private final AdminRepository adminRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    @PostMapping("/login")
    @Operation(summary = "Admin login")
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody LoginRequest request) {
        Admin admin = adminRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Admin not found"));

        if (!passwordEncoder.matches(request.getPassword(), admin.getPassword())) {
            throw new BadCredentialsException("Invalid password");
        }

        String jwt = jwtUtils.generateToken(new AdminDetailsImpl(admin), "admin");

        return ResponseEntity.ok(new JwtResponse(jwt, admin.getEmail(), "Bearer"));
    }
}
