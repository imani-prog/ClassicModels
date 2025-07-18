package com.classicmodels.classicmodels.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Override
    public void sendPasswordResetEmail(String toEmail, String resetToken) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(toEmail);
            message.setSubject("Password Reset Request");
            message.setText(buildPasswordResetEmailContent(resetToken));

            mailSender.send(message);
            log.info("Password reset email sent to: {}", toEmail);
        } catch (Exception e) {
            log.error("Failed to send password reset email to: {}", toEmail, e);
            throw new RuntimeException("Failed to send password reset email");
        }
    }

    private String buildPasswordResetEmailContent(String resetToken) {
        return String.format(
            "Hello,\n\n" +
            "You have requested to reset your password. Please use the following token to reset your password:\n\n" +
            "Reset Token: %s\n\n" +
            "This token will expire in 1 hour.\n\n" +
            "If you didn't request this password reset, please ignore this email.\n\n" +
            "Best regards,\n" +
            "Classic Models Team",
            resetToken
        );
    }
}
