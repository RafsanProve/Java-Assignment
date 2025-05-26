package com.example.java_assignment.service;

import com.example.java_assignment.dto.RegistrationDTO;
import org.springframework.http.ResponseEntity;
//import com.example.java_assignment.model.AppUser;

public interface AppUserService {
    ResponseEntity<String> saveUser(RegistrationDTO user);
}
