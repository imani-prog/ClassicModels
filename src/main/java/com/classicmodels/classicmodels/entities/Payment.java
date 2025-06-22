package com.classicmodels.classicmodels.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;


@Entity
@Table(name = "payments")
@Getter
@Setter
@NoArgsConstructor
public class Payment {

    @EmbeddedId
    private PaymentId id;

    @Column(name = "paymentDate", nullable = false)
    private LocalDate paymentDate;

    @Column(name = "amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_number", insertable = false, updatable = false)
    @JsonIgnore
    private Customer customer;

    public void setCustomer(Customer customer) {
        this.customer = customer;
        if (this.id == null) {
            this.id = new PaymentId();
        }
        this.id.setCustomerNumber(customer.getCustomerNumber());
    }
}
