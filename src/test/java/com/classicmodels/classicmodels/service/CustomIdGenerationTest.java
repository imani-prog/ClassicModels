package com.classicmodels.classicmodels.service;

import com.classicmodels.classicmodels.dto.RegistrationDto;
import com.classicmodels.classicmodels.dto.UserDto;
import com.classicmodels.classicmodels.entities.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class CustomIdGenerationTest {

    @Autowired
    private UserService userService;

    @Test
    public void testCustomIdGeneration() {
        // Create registration data for Timothy (admin)
        RegistrationDto registrationDto = new RegistrationDto();
        registrationDto.setFirstName("Timothy");
        registrationDto.setLastName("Imani");
        registrationDto.setEmail("timothy.test@example.com");
        registrationDto.setPassword("Timo9380");
        registrationDto.setConfirmPassword("Timo9380");

        // Register the user
        UserDto userDto = userService.registerUser(registrationDto);

        // Verify user was created with custom ID
        assertNotNull(userDto);
        assertNotNull(userDto.getId());
        assertTrue(userDto.getId().startsWith("AD")); // Should start with AD for admin
        assertEquals("AD001", userDto.getId()); // First admin should be AD001

        System.out.println("✅ User registered with custom ID: " + userDto.getId());
        System.out.println("📧 Email: " + userDto.getEmail());
        System.out.println("👤 Name: " + userDto.getFirstName() + " " + userDto.getLastName());
        System.out.println("🎯 Role: " + userDto.getRole());
    }

    @Test
    public void testMultipleAdminIdGeneration() {
        // Register first admin
        RegistrationDto admin1 = new RegistrationDto();
        admin1.setFirstName("Admin");
        admin1.setLastName("One");
        admin1.setEmail("admin1@example.com");
        admin1.setPassword("Password123");
        admin1.setConfirmPassword("Password123");

        UserDto user1 = userService.registerUser(admin1);
        assertEquals("AD001", user1.getId());

        // Register second admin
        RegistrationDto admin2 = new RegistrationDto();
        admin2.setFirstName("Admin");
        admin2.setLastName("Two");
        admin2.setEmail("admin2@example.com");
        admin2.setPassword("Password123");
        admin2.setConfirmPassword("Password123");

        UserDto user2 = userService.registerUser(admin2);
        assertEquals("AD002", user2.getId());

        System.out.println("✅ Sequential ID generation working:");
        System.out.println("👤 First admin: " + user1.getId());
        System.out.println("👤 Second admin: " + user2.getId());
    }
}
