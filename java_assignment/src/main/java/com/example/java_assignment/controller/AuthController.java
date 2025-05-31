package com.example.java_assignment.controller;

import com.example.java_assignment.dto.RegistrationDTO;
import com.example.java_assignment.dto.JwtResponse;
import com.example.java_assignment.dto.LoginRequest;
import com.example.java_assignment.exception.ResourceNotFoundException;
import com.example.java_assignment.model.AppUser;
import com.example.java_assignment.repository.AppUserRepository;
import com.example.java_assignment.security.JwtUtils;
import com.example.java_assignment.service.AppUserService;
import com.example.java_assignment.service.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "User Auth")
public class AuthController {

    @Autowired
    private final AuthenticationManager authenticationManager;

    @Autowired
    private final AppUserRepository userRepository;

    @Autowired
    private final JwtUtils jwtUtils;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private AppUserService userService;


    @PostMapping("/login")
    @Operation(summary = "User login")
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody LoginRequest request) {
        return userService.validateLogin(request);
    }

    @PostMapping("/register")
    @Operation(summary = "Register a new user")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "User registered successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input or user already exists")
    })
    public ResponseEntity<String> register(@Valid @RequestBody RegistrationDTO request) {
        return userService.saveUser(request);
    }

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Auth endpoint is working!");
    }


}

