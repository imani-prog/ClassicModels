package com.classicmodels.classicmodels.repository;

import com.classicmodels.classicmodels.entities.Payment;
import com.classicmodels.classicmodels.entities.PaymentId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, PaymentId> {
    List<Payment> findTop10ByOrderByAmountDesc();

    @Query("SELECT COUNT(p) FROM Payment p WHERE p.id.checkNumber LIKE ?1")
    long countByCheckNumberStartingWith(String prefix);

    @Query("SELECT p.id.checkNumber FROM Payment p WHERE p.id.checkNumber LIKE 'CHK-%' ORDER BY p.id.checkNumber DESC")
    List<String> findAllCheckNumbersOrderedDesc();
}
