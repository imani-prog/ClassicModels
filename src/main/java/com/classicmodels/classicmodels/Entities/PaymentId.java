package com.classicmodels.classicmodels.Entities;

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
public class PaymentId implements Serializable {
    private static final long serialVersionUID = 2745661273467188313L;
    @Column(name = "customerNumber", nullable = false)
    private Integer customerNumber;

    @Column(name = "checkNumber", nullable = false, length = 50)
    private String checkNumber;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || ProxyUtils.getUserClass(this) != ProxyUtils.getUserClass(o)) return false;
        PaymentId entity = (PaymentId) o;
        return Objects.equals(this.checkNumber, entity.checkNumber) &&
                Objects.equals(this.customerNumber, entity.customerNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(checkNumber, customerNumber);
    }

}