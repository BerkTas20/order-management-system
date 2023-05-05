package com.berktas.ordermanagementsystem.service;

import com.berktas.ordermanagementsystem.controller.requests.SaveAndUpdateCustomerRequest;
import com.berktas.ordermanagementsystem.model.entity.Customer;

import java.util.List;
import java.util.Map;

public interface CustomerService {

    Customer create(SaveAndUpdateCustomerRequest createCustomer);

    Customer update(Long customerId, SaveAndUpdateCustomerRequest updateCustomer);

    List<Customer> getAll();

    void delete(Long customerId);

    Customer getById(Long customerId);

    Map<String, List<Long>> searchCustomersAndOrdersByKeyword(String keyword);

    List<Customer> findCustomersWithoutOrders();
}
