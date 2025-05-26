package com.example.java_assignment.service.implementation;

import com.example.java_assignment.dto.RegistrationDTO;
import com.example.java_assignment.model.AppUser;
import com.example.java_assignment.repository.AppUserRepository;
import com.example.java_assignment.security.JwtUtils;
import com.example.java_assignment.service.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@AllArgsConstructor
@Service
public class AppUserServiceImpl implements AppUserService {

    private AppUserRepository appUserRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public ResponseEntity<String> saveUser(RegistrationDTO request) {
        try {
            boolean userByEmail = appUserRepository.findByEmail(request.getEmail()).isPresent();
            boolean userByUsername = appUserRepository.findByUsername(request.getUsername()).isPresent();
            if (userByEmail) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body("Error: Email is already registered");
            }

            if (userByUsername) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body("Error: Username is already taken");
            }

            AppUser user = new AppUser();
            user.setUsername(request.getUsername());
            user.setEmail(request.getEmail());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setCreatedAt(LocalDateTime.now());
            user.setUpdatedAt(LocalDateTime.now());

            appUserRepository.save(user);
            return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
        } catch (Exception e) {
            e.printStackTrace(); // Print root cause
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }
}
