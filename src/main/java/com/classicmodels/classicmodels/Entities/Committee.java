package com.classicmodels.classicmodels.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "committees")
public class Committee {
    @Id
    @Column(name = "committee_id", nullable = false)
    private Integer id;

    @Column(name = "name", length = 100)
    private String name;

}