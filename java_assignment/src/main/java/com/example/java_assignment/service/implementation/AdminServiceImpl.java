package com.example.java_assignment.service.implementation;

import com.example.java_assignment.model.Admin;
import com.example.java_assignment.repository.AdminRepository;
import com.example.java_assignment.service.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private final AdminRepository adminRepository;

    @Override
    public ResponseEntity<Admin> loadAdminByUsername(String username) {
        return null;
    }
}
