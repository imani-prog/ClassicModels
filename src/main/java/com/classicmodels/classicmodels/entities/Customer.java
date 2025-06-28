package com.classicmodels.classicmodels.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "customers")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Customer {
    @Id
    @Column(name = "customerNumber", nullable = false)
    private Integer id;

    @Column(name = "customerName", nullable = false, length = 50)
    private String customerName;

    @Column(name = "contactLastName", nullable = false, length = 50)
    private String contactLastName;

    @Column(name = "contactFirstName", nullable = false, length = 50)
    private String contactFirstName;

    @Column(name = "phone", nullable = false, length = 50)
    private String phone;

    @Column(name = "addressLine1", nullable = false, length = 50)
    private String addressLine1;

    @Column(name = "addressLine2", length = 50)
    private String addressLine2;

    @Column(name = "city", nullable = false, length = 50)
    private String city;

    @Column(name = "state", length = 50)
    private String state;

    @Column(name = "postalCode", length = 15)
    private String postalCode;

    @Column(name = "country", nullable = false, length = 50)
    private String country;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "salesRepEmployeeNumber")
    // Ensure no cascade to avoid persisting transient Employee
    private Employee salesRepEmployeeNumber;

    @Column(name = "creditLimit", precision = 10, scale = 2)
    private BigDecimal creditLimit;

//    @Column(name = "createdAt", nullable = false)
//    private java.time.LocalDateTime createdAt;
//
//    @PrePersist
//    protected void onCreate() {
//        if (createdAt == null) {
//            createdAt = java.time.LocalDateTime.now();
//        }
//    }

    public Integer getCustomerNumber() {
        return id;
    }

}