package com.example.java_assignment.security;

import jakarta.servlet.http.HttpServletRequest;

public class AuthUtils {
    public static boolean isAdmin(HttpServletRequest request) {
        String type = (String) request.getAttribute("userType");
        return "admin".equalsIgnoreCase(type);
    }
}

