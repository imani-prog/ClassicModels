package com.classicmodels.classicmodels.service;

import com.classicmodels.classicmodels.dto.*;
import com.classicmodels.classicmodels.entities.User;

public interface UserService {

    UserDto registerUser(RegistrationDto registrationDto);

    LoginResponseDto loginUser(LoginDto loginDto);

    UserDto findByEmail(String email);

    boolean existsByEmail(String email);

    UserDto convertToDto(User user);

    User convertToEntity(RegistrationDto registrationDto);

    // Password reset methods
    void initiatePasswordReset(String email);

    void resetPassword(PasswordResetDto passwordResetDto);

    boolean isValidResetToken(String token);
}
