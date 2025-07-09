package com.classicmodels.classicmodels.service;

import com.classicmodels.classicmodels.dto.LoginDto;
import com.classicmodels.classicmodels.dto.LoginResponseDto;
import com.classicmodels.classicmodels.dto.RegistrationDto;
import com.classicmodels.classicmodels.dto.UserDto;
import com.classicmodels.classicmodels.entities.User;

public interface UserService {

    UserDto registerUser(RegistrationDto registrationDto);

    LoginResponseDto loginUser(LoginDto loginDto);

    UserDto findByEmail(String email);

    boolean existsByEmail(String email);

    UserDto convertToDto(User user);

    User convertToEntity(RegistrationDto registrationDto);
}
