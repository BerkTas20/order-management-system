package com.berktas.ordermanagementsystem.repository;

import com.berktas.ordermanagementsystem.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    List<Customer> findByNameContaining(String keyword);

    @Query("SELECT c FROM Customer c LEFT JOIN c.orders o WHERE o.id IS NULL")
    List<Customer> findCustomersWithoutOrders();

}
