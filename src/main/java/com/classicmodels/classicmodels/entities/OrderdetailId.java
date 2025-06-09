package com.classicmodels.classicmodels.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.util.ProxyUtils;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class OrderdetailId implements Serializable {
    private static final long serialVersionUID = 4708156443048968190L;
    @Column(name = "orderNumber", nullable = false)
    private Integer orderNumber;

    @Column(name = "productCode", nullable = false, length = 15)
    private String productCode;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || ProxyUtils.getUserClass(this) != ProxyUtils.getUserClass(o)) return false;
        OrderdetailId entity = (OrderdetailId) o;
        return Objects.equals(this.orderNumber, entity.orderNumber) &&
                Objects.equals(this.productCode, entity.productCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderNumber, productCode);
    }

}