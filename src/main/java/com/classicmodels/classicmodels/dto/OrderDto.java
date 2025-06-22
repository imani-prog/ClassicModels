package com.classicmodels.classicmodels.dto;

import java.time.LocalDate;

public class OrderDto {
    public Integer id;
    public LocalDate orderDate;
    public LocalDate requiredDate;
    public LocalDate shippedDate;
    public String status;
    public String comments;
    public Integer customerNumber;
}

