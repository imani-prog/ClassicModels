package com.classicmodels.classicmodels.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "productlines")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Productline {
    @Id
    @Column(name = "productLine", nullable = false, length = 50)
    private String productLine;

    @Column(name = "textDescription", length = 4000)
    private String textDescription;

    @Lob
    @Column(name = "htmlDescription")
    private String htmlDescription;

    @Column(name = "image")
    private byte[] image;

    public Productline(String productLine) {
        this.productLine = productLine;
    }

    public Productline() {
        // Default constructor
    }

    // Optionally, you can add a static factory for Jackson:
    @com.fasterxml.jackson.annotation.JsonCreator
    public static Productline fromString(String productLine) {
        return new Productline(productLine);
    }
}