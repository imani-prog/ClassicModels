package com.classicmodels.classicmodels.service;

import com.classicmodels.classicmodels.dto.LoginDto;
import com.classicmodels.classicmodels.dto.LoginResponseDto;
import com.classicmodels.classicmodels.dto.PasswordResetDto;
import com.classicmodels.classicmodels.dto.RegistrationDto;
import com.classicmodels.classicmodels.dto.UserDto;
import com.classicmodels.classicmodels.entities.User;
import com.classicmodels.classicmodels.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImplementation implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserIdGenerator userIdGenerator;
    private final EmailService emailService;

    private static final String SECRET_KEY = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";
    private static final long JWT_EXPIRATION = 1000 * 60 * 60 * 24; // 24 hours

    @Override
    public UserDto registerUser(RegistrationDto registrationDto) {
        if (!registrationDto.getPassword().equals(registrationDto.getConfirmPassword())) {
            throw new RuntimeException("Passwords do not match");
        }

        if (existsByEmail(registrationDto.getEmail())) {
            throw new RuntimeException("User with this email already exists");
        }
        User user = convertToEntity(registrationDto);

        String customId = userIdGenerator.generateUserId(user.getRole());
        user.setId(customId);

        user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));

        User savedUser = userRepository.save(user);
        return convertToDto(savedUser);
    }

    @Override
    public UserDto findByEmail(String email) {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
        return convertToDto(user);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public UserDto convertToDto(User user) {
        return new UserDto(
            user.getId(),
            user.getFirstName(),
            user.getLastName(),
            user.getEmail(),
            user.getRole().name(),
            user.isEnabled()
        );
    }

    @Override
    public User convertToEntity(RegistrationDto registrationDto) {
        User user = new User();
        user.setFirstName(registrationDto.getFirstName());
        user.setLastName(registrationDto.getLastName());
        user.setEmail(registrationDto.getEmail());
        user.setRole(User.Role.ADMIN); // All registered users are admins
        user.setEnabled(true);
        return user;
    }

    @Override
    public LoginResponseDto loginUser(LoginDto loginDto) {
        try {

            User user = userRepository.findByEmail(loginDto.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

            if (!user.isEnabled()) {
                return new LoginResponseDto(false, "Account is disabled");
            }

            if (!passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
                return new LoginResponseDto(false, "Invalid email or password");
            }

            UserDto userDto = convertToDto(user);
            String token = generateToken(user.getEmail(), user.getId(), user.getRole().name());
            log.info("Login successful for user: {} with role: {}", user.getEmail(), user.getRole());
            return new LoginResponseDto(true, "Login successful", token, userDto);
        } catch (Exception e) {
            log.error("Login failed for email: {}, error: {}", loginDto.getEmail(), e.getMessage());
            return new LoginResponseDto(false, "Login failed: " + e.getMessage());
        }
    }

    private String generateToken(String userEmail, String userId, String role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        claims.put("role", role);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userEmail)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION))
                .signWith(SignatureAlgorithm.HS256, getSignInKey())
                .compact();
    }

    private Key getSignInKey() {
        byte[] keyBytes = hexStringToByteArray(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }

    @Override
    public void initiatePasswordReset(String email) {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found with email: " + email));

        // Generate reset token
        String resetToken = generateResetToken();

        // Set token and expiry (1 hour from now)
        user.setResetToken(resetToken);
        user.setResetTokenExpiry(LocalDateTime.now().plusHours(1));

        userRepository.save(user);

        // Send email
        emailService.sendPasswordResetEmail(email, resetToken);

        log.info("Password reset initiated for email: {}", email);
    }

    @Override
    public void resetPassword(PasswordResetDto passwordResetDto) {
        // Validate passwords match
        if (!passwordResetDto.getNewPassword().equals(passwordResetDto.getConfirmPassword())) {
            throw new RuntimeException("Passwords do not match");
        }

        // Find user by reset token
        User user = userRepository.findByResetToken(passwordResetDto.getToken())
            .orElseThrow(() -> new RuntimeException("Invalid reset token"));

        // Check if token is expired
        if (user.getResetTokenExpiry().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Reset token has expired");
        }

        // Update password
        user.setPassword(passwordEncoder.encode(passwordResetDto.getNewPassword()));
        user.setResetToken(null);
        user.setResetTokenExpiry(null);

        userRepository.save(user);

        log.info("Password reset successful for user: {}", user.getEmail());
    }

    @Override
    public boolean isValidResetToken(String token) {
        return userRepository.findByResetToken(token)
            .map(user -> user.getResetTokenExpiry().isAfter(LocalDateTime.now()))
            .orElse(false);
    }

    private String generateResetToken() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
