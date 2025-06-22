package com.classicmodels.classicmodels.service;

import com.classicmodels.classicmodels.entities.Payment;
import com.classicmodels.classicmodels.entities.PaymentId;
import java.util.List;
import java.util.Optional;

public interface PaymentService {
    Payment savePayment(Payment payment);
    Optional<Payment> getPaymentById(PaymentId id);
    Payment updatePayment(PaymentId id, Payment payment);
    boolean deletePayment(PaymentId id);
    List<Payment> getAllPayments();
}
