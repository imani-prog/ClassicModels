package com.classicmodels.classicmodels.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "orderdetails")
public class Orderdetail {
    @EmbeddedId
    private OrderdetailId id;

    @MapsId("orderNumber")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "orderNumber", nullable = false)
    private Order orderNumber;

    @MapsId("productCode")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "productCode", nullable = false)
    private Product productCode;

    @Column(name = "quantityOrdered", nullable = false)
    private Integer quantityOrdered;

    @Column(name = "priceEach", nullable = false, precision = 10, scale = 2)
    private BigDecimal priceEach;

    @Column(name = "orderLineNumber", nullable = false)
    private Short orderLineNumber;

}