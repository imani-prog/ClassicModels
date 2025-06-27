package com.classicmodels.classicmodels.dto;

import java.time.LocalDate;

public interface OrderStatusTrendDTO {
    String getStatus();
    LocalDate getOrderDate();
    Long getCount();
}

