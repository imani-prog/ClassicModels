package com.classicmodels.classicmodels.dto;

import java.time.LocalDate;

public class OrderResponseDto {
    public Integer id;
    public LocalDate orderDate;
    public LocalDate requiredDate;
    public LocalDate shippedDate;
    public String status;
    public String comments;
    public Integer customerNumber;
    public String customerName;

    public OrderResponseDto(Integer id, LocalDate orderDate, LocalDate requiredDate, LocalDate shippedDate, String status, String comments, Integer customerNumber, String customerName) {
        this.id = id;
        this.orderDate = orderDate;
        this.requiredDate = requiredDate;
        this.shippedDate = shippedDate;
        this.status = status;
        this.comments = comments;
        this.customerNumber = customerNumber;
        this.customerName = customerName;
    }
}

