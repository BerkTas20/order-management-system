package com.berktas.ordermanagementsystem.service.impl;

import com.berktas.ordermanagementsystem.controller.requests.SaveAndUpdateCustomerRequest;
import com.berktas.ordermanagementsystem.model.entity.Customer;
import com.berktas.ordermanagementsystem.model.entity.Order;
import com.berktas.ordermanagementsystem.repository.CustomerRepository;
import com.berktas.ordermanagementsystem.repository.OrderRepository;
import com.berktas.ordermanagementsystem.service.CustomerService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;


    @Override
    public Customer create(SaveAndUpdateCustomerRequest createCustomer) {
        Customer customer = Customer.builder()
                .name(createCustomer.getName())
                .age(createCustomer.getAge()).build();

        return customerRepository.save(customer);
    }


    @Override
    public Customer update(Long customerId, SaveAndUpdateCustomerRequest updateCustomer) {
        Customer customerToUpdate = customerRepository.findById(customerId).orElseThrow(() -> new EntityNotFoundException("Customer not found with id: " + customerId));

        customerToUpdate.setName(updateCustomer.getName());
        customerToUpdate.setAge(updateCustomer.getAge());

        return customerRepository.save(customerToUpdate);
    }

    @Override
    public List<Customer> getAll() {
        return customerRepository.findAll();
    }

    @Override
    public void delete(Long customerId) {
        customerRepository.deleteById(customerId);
    }

    @Override
    public Customer getById(Long customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new EntityNotFoundException("Customer not found with id: " + customerId));
        return customer;
    }


// TODO: 19.05.2023  : 19.05.2023      When you enter the customer's name as keyword, it brings the id if there is an order.
    @Override
    public Map<String, List<Long>> searchCustomersAndOrdersByKeyword(String keyword) {
        List<Customer> customers = customerRepository.findByNameContaining(keyword);
        List<Order> orders = orderRepository.findByCustomerIn(customers);

        return customers.stream()
                .collect(Collectors.toMap(
                   Customer::getName,
                   customer -> orders.stream()
                           .filter(order -> order.getCustomer().equals(customer))
                           .map(Order::getId)
                           .collect(Collectors.toList())
                ));
    }
    //OTHER USE
//    List<Customer> customers = customerRepository.findByNameContaining(keyword);
//    List<Order> orders = orderRepository.findByCustomerIn(customers);
//
//    Map<String, List<Long>> result = new HashMap<>();
//        for (Customer customer : customers) {
//        List<Long> orderIds = new ArrayList<>();
//        for (Order order : orders) {
//            if (order.getCustomer().equals(customer)) {
//                orderIds.add(order.getId());
//            }
//        }
//        result.put(customer.getName(), orderIds);
//    }
//        return result;
//}

    @Override
    public List<Customer> findCustomersWithoutOrders() {
        return customerRepository.findCustomersWithoutOrders();
    }
}
