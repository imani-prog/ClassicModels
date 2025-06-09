package com.classicmodels.classicmodels.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "payments")
public class Payment {
    @EmbeddedId
    private PaymentId id;

    @MapsId("customerNumber")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customerNumber", nullable = false)
    private Customer customerNumber;

    @Column(name = "paymentDate", nullable = false)
    private LocalDate paymentDate;

    @Column(name = "amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

}