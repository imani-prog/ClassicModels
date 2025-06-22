package com.classicmodels.classicmodels.service;

import com.classicmodels.classicmodels.entities.Payment;
import com.classicmodels.classicmodels.entities.PaymentId;
import com.classicmodels.classicmodels.repository.PaymentRepository;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaymentServiceImplementation implements PaymentService {
    private final PaymentRepository paymentRepository;

    @Override
    public Payment savePayment(Payment payment) {
        return paymentRepository.save(payment);
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
