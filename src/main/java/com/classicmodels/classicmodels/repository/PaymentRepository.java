package com.classicmodels.classicmodels.repository;

import com.classicmodels.classicmodels.entities.Payment;
import com.classicmodels.classicmodels.entities.PaymentId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, PaymentId> {
}
