package com.classicmodels.classicmodels.controllers;

import com.classicmodels.classicmodels.dto.LoginDto;
import com.classicmodels.classicmodels.dto.LoginResponseDto;
import com.classicmodels.classicmodels.dto.RegistrationDto;
import com.classicmodels.classicmodels.dto.UserDto;
import com.classicmodels.classicmodels.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegistrationDto registrationDto) {
        log.info("Register request received for email: {}", registrationDto.getEmail());
        try {
            UserDto userDto = userService.registerUser(registrationDto);
            return ResponseEntity.ok(userDto);
        } catch (RuntimeException e) {
            log.error("Registration failed: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/check-email")
    public ResponseEntity<?> checkEmailExists(@RequestBody String email) {
        boolean exists = userService.existsByEmail(email);
        return ResponseEntity.ok(exists);
    }

    @GetMapping("/user")
    public ResponseEntity<?> getCurrentUser(@RequestParam String email) {
        log.info("Get current user request for email: {}", email);
        try {
            UserDto userDto = userService.findByEmail(email);
            log.info("User found: {} with role: {}", userDto.getEmail(), userDto.getRole());
            return ResponseEntity.ok(userDto);
        } catch (RuntimeException e) {
            log.error("User not found for email: {}", email);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> loginUser(@Valid @RequestBody LoginDto loginDto) {
        log.info("Login request received for email: {}", loginDto.getEmail());
        LoginResponseDto response = userService.loginUser(loginDto);

        if (response.isSuccess()) {
            log.info("Login successful for email: {} with role: {}", loginDto.getEmail(), response.getUser().getRole());
            log.info("Full user object: {}", response.getUser());
            return ResponseEntity.ok(response);
        } else {
            log.warn("Login failed for email: {} - {}", loginDto.getEmail(), response.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    // Testing endpoint to verify user data and permissions
    @GetMapping("/verify-role")
    public ResponseEntity<?> verifyUserRole(@RequestParam String email) {
        log.info("Role verification request for email: {}", email);
        try {
            UserDto userDto = userService.findByEmail(email);
            Map<String, Object> response = new HashMap<>();
            response.put("email", userDto.getEmail());
            response.put("role", userDto.getRole());
            response.put("enabled", userDto.isEnabled());
            response.put("hasAdminRole", "ADMIN".equals(userDto.getRole()));
            response.put("hasUserRole", "USER".equals(userDto.getRole()));
            response.put("canAccessDashboard", true); // Since all users are ADMIN by default

            log.info("Role verification - User: {}, Role: {}, IsAdmin: {}",
                    userDto.getEmail(), userDto.getRole(), "ADMIN".equals(userDto.getRole()));

            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            log.error("Role verification failed for email: {}", email);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Testing endpoint to check if auth is working
    @GetMapping("/test-auth")
    public ResponseEntity<?> testAuth() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Auth endpoint is working");
        response.put("timestamp", System.currentTimeMillis());
        response.put("server", "Spring Boot Backend");
        return ResponseEntity.ok(response);
    }
}
