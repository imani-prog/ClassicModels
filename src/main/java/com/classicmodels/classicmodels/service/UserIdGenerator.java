package com.classicmodels.classicmodels.service;

import com.classicmodels.classicmodels.entities.User;
import com.classicmodels.classicmodels.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserIdGenerator {

    @Autowired
    private UserRepository userRepository;

    public String generateUserId(User.Role role) {
        String prefix = getPrefix(role);

        // Find the highest existing ID for this role
        String lastId = userRepository.findLastIdByRole(role);

        int nextNumber = 1;
        if (lastId != null && !lastId.isEmpty()) {
            // Extract the number part from the last ID (e.g., "AD001" -> "001")
            String numberPart = lastId.substring(prefix.length());
            nextNumber = Integer.parseInt(numberPart) + 1;
        }

        // Format with leading zeros (e.g., 1 -> "001")
        return prefix + String.format("%03d", nextNumber);
    }

    private String getPrefix(User.Role role) {
        switch (role) {
            case ADMIN:
                return "AD";
            case USER:
                return "US";
            default:
                return "UN"; // Unknown
        }
    }
}
