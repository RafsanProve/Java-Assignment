package com.example.java_assignment.service.implementation;

import com.example.java_assignment.dto.JwtResponse;
import com.example.java_assignment.exception.ResourceNotFoundException;
import com.example.java_assignment.model.Admin;
import com.example.java_assignment.model.AppUser;
import com.example.java_assignment.repository.AdminRepository;
import com.example.java_assignment.repository.AppUserRepository;
import com.example.java_assignment.security.JwtUtils;
import com.example.java_assignment.service.AdminDetailsImpl;
import com.example.java_assignment.service.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private final AdminRepository adminRepository;

    @Autowired
    private final AppUserRepository userRepository;

    @Autowired
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private final JwtUtils jwtUtils;

    @Override
    public ResponseEntity<Admin> loadAdminByUsername(String username) {
        return null;
    }

    @Override
    public ResponseEntity<JwtResponse> validateLogin(String credential, String password) {
        try{
            Admin admin = adminRepository.findByEmailOrUsername(credential, credential)
                    .orElseThrow(() -> new ResourceNotFoundException("Admin not found"));

            if (!passwordEncoder.matches(password, admin.getPassword())) {
                throw new BadCredentialsException("Invalid password");
            }

            String jwt = jwtUtils.generateToken(new AdminDetailsImpl(admin), "ADMIN");

            return ResponseEntity.ok(new JwtResponse(jwt, admin.getEmail(), "Bearer"));
        }
        catch(Exception e){
            e.printStackTrace();
            throw e; //new BadCredentialsException("Invalid credentials");
        }
    }

    @Override
    public ResponseEntity<List<AppUser>> getAllUsers() {
        List<AppUser> users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }

    @Override
    public ResponseEntity<JwtResponse> saveAdmin(String email, String password) {
        try{
            Admin admin = new Admin();
            admin.setEmail(email);
            admin.setPassword(passwordEncoder.encode(password));
            admin.setCreatedAt(LocalDateTime.now());
            admin.setUpdatedAt(LocalDateTime.now());

            adminRepository.save(admin);
            return ResponseEntity.ok(new JwtResponse("Admin registered successfully", admin.getEmail(), "Bearer"));
        }
        catch(Exception e){
            e.printStackTrace();
            throw e;
        }
    }


}
