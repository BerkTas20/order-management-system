package com.berktas.ordermanagementsystem.repository;

import com.berktas.ordermanagementsystem.model.entity.Customer;
import com.berktas.ordermanagementsystem.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByCreateDateAfter(LocalDateTime date);

    List<Order> findByCustomerIn(List<Customer> customers);
}
