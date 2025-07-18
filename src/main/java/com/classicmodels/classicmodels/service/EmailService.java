package com.classicmodels.classicmodels.service;

public interface EmailService {
    void sendPasswordResetEmail(String toEmail, String resetToken);
}
