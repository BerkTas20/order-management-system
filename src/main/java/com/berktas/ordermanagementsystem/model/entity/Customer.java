package com.berktas.ordermanagementsystem.model.entity;


import com.berktas.ordermanagementsystem.model.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Customer extends BaseEntity {

    private String name;

    private int age;

    @OneToMany(mappedBy = "customer")
    @JsonIgnoreProperties("customer")
    private List<Order> orders;

}
