package com.classicmodels.classicmodels.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "productlines")
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

}