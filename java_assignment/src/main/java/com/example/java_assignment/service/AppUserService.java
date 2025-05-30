package com.example.java_assignment.service;

import com.example.java_assignment.dto.JwtResponse;
import com.example.java_assignment.dto.LoginRequest;
import com.example.java_assignment.dto.RegistrationDTO;
import org.springframework.http.ResponseEntity;

public interface AppUserService {
    ResponseEntity<String> saveUser(RegistrationDTO request);
    ResponseEntity<JwtResponse> validateLogin(LoginRequest request);
}
