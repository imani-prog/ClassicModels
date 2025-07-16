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

        String lastId = userRepository.findLastIdByRole(role);

        int nextNumber = 1;
        if (lastId != null && !lastId.isEmpty()) {
            String numberPart = lastId.substring(prefix.length());
            nextNumber = Integer.parseInt(numberPart) + 1;
        }

        return prefix + String.format("%03d", nextNumber);
    }

    private String getPrefix(User.Role role) {
        switch (role) {
            case ADMIN:
                return "AD";
            case USER:
                return "US";
            default:
                return "UN";
        }
    }
}
