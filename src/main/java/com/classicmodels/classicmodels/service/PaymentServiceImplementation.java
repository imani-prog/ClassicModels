package com.classicmodels.classicmodels.service;

import com.classicmodels.classicmodels.entities.Payment;
import com.classicmodels.classicmodels.entities.PaymentId;
import com.classicmodels.classicmodels.repository.PaymentRepository;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentServiceImplementation implements PaymentService {
    private final PaymentRepository paymentRepository;

    @Override
    public Payment savePayment(Payment payment) {
        try {
            // Auto-generate check number if not provided
            if (payment.getId() == null) {
                payment.setId(new PaymentId());
            }

            // Set customer number in PaymentId if not already set
            if (payment.getCustomer() != null && payment.getCustomer().getCustomerNumber() != null) {
                payment.getId().setCustomerNumber(payment.getCustomer().getCustomerNumber());
            }

            if (payment.getId().getCheckNumber() == null || payment.getId().getCheckNumber().isEmpty()) {
                payment.getId().setCheckNumber(generateCheckNumber());
            }

            log.info("Saving payment with customer: {} and check number: {}",
                    payment.getId().getCustomerNumber(), payment.getId().getCheckNumber());

            return paymentRepository.save(payment);

        } catch (Exception e) {
            log.error("Error saving payment: ", e);
            throw e;
        }
    }

    private String generateCheckNumber() {
        try {
            // Get the count of existing payments to determine the next number
            long paymentCount = paymentRepository.count();

            // Generate sequential number starting from 001
            int nextNumber = (int) (paymentCount + 1);

            // Format as CHK-XXX with leading zeros
            String checkNumber = String.format("CHK-%03d", nextNumber);

            // Check if this check number already exists (in case of gaps in numbering)
            while (checkNumberExists(checkNumber)) {
                nextNumber++;
                checkNumber = String.format("CHK-%03d", nextNumber);
            }

            log.info("Generated check number: {}", checkNumber);
            return checkNumber;

        } catch (Exception e) {
            // Fallback to timestamp-based generation if there's an error
            String fallbackCheck = "CHK-" + (System.currentTimeMillis() % 100000);
            log.warn("Error generating sequential check number, using fallback: {}", fallbackCheck);
            return fallbackCheck;
        }
    }

    private boolean checkNumberExists(String checkNumber) {
        // Check if any payment exists with this check number
        return paymentRepository.countByCheckNumberStartingWith(checkNumber) > 0;
    }

    @Override
    public Optional<Payment> getPaymentById(PaymentId id) {
        return paymentRepository.findById(id);
    }

    @Override
    public Payment updatePayment(PaymentId id, Payment payment) {
        if (!paymentRepository.existsById(id)) {
            return null;
        }
        payment.setId(id);
        return paymentRepository.save(payment);
    }

    @Override
    public boolean deletePayment(PaymentId id) {
        if (!paymentRepository.existsById(id)) {
            return false;
        }
        paymentRepository.deleteById(id);
        return true;
    }

    @Override
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }
}
