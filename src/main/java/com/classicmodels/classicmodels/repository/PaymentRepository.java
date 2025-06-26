package com.classicmodels.classicmodels.repository;

import com.classicmodels.classicmodels.entities.Payment;
import com.classicmodels.classicmodels.entities.PaymentId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, PaymentId> {
    List<Payment> findTop10ByOrderByAmountDesc();
}
