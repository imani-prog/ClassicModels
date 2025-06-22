package com.classicmodels.classicmodels.controllers;

import com.classicmodels.classicmodels.entities.Payment;
import com.classicmodels.classicmodels.entities.PaymentId;
import com.classicmodels.classicmodels.repository.CustomerRepository;
import com.classicmodels.classicmodels.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;
    private final CustomerRepository customerRepository;

    @PostMapping("/save")
    public ResponseEntity<Payment> savePayment(@RequestBody Payment payment) {
        PaymentId paymentId = payment.getId();

        if (paymentId == null || paymentId.getCustomerNumber() == null || paymentId.getCheckNumber() == null) {
            throw new IllegalArgumentException("Payment ID with customerNumber and checkNumber is required.");
        }
        return ResponseEntity.ok(paymentService.savePayment(payment));
    }

    @GetMapping("/{customerNumber}/{checkNumber}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable Integer customerNumber, @PathVariable String checkNumber) {
        PaymentId id = new PaymentId();
        id.setCustomerNumber(customerNumber);
        id.setCheckNumber(checkNumber);
        return paymentService.getPaymentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{customerNumber}/{checkNumber}")
    public ResponseEntity<Payment> updatePayment(@PathVariable Integer customerNumber, @PathVariable String checkNumber, @RequestBody Payment payment) {
        PaymentId id = new PaymentId();
        id.setCustomerNumber(customerNumber);
        id.setCheckNumber(checkNumber);
        customerRepository.findById(customerNumber)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found with ID: " + customerNumber));

        payment.setCustomer(customerRepository.getReferenceById(customerNumber));

        Payment updated = paymentService.updatePayment(id, payment);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{customerNumber}/{checkNumber}")
    public ResponseEntity<Void> deletePayment(@PathVariable Integer customerNumber, @PathVariable String checkNumber) {
        PaymentId id = new PaymentId();
        id.setCustomerNumber(customerNumber);
        id.setCheckNumber(checkNumber);
        boolean deleted = paymentService.deletePayment(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping("")
    public ResponseEntity<List<Payment>> getAllPayments() {
        return ResponseEntity.ok(paymentService.getAllPayments());
    }
}
