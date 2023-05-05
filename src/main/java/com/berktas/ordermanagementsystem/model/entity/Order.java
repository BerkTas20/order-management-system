package com.berktas.ordermanagementsystem.model.entity;


import com.berktas.ordermanagementsystem.model.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
public class Order extends BaseEntity {

    private LocalDateTime createDate;

    private double totalPrice;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

}
