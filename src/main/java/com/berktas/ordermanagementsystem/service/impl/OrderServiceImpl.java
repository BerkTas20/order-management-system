package com.berktas.ordermanagementsystem.service.impl;

import com.berktas.ordermanagementsystem.controller.requests.SaveAndUpdateOrderRequest;
import com.berktas.ordermanagementsystem.model.entity.Customer;
import com.berktas.ordermanagementsystem.model.entity.Order;
import com.berktas.ordermanagementsystem.repository.CustomerRepository;
import com.berktas.ordermanagementsystem.repository.OrderRepository;
import com.berktas.ordermanagementsystem.service.OrderService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;


    @Override
    public Order create(SaveAndUpdateOrderRequest createOrder, Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found with id: " + customerId));

        Order order = Order.builder()
                .createDate(LocalDateTime.now())
                .totalPrice(createOrder.getTotalPrice())
                .customer(customer)
                .build();

        return orderRepository.save(order);
    }

    @Override
    public Order update(Long orderId, SaveAndUpdateOrderRequest updateOrder) {
        Order orderToUpdate = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found with id: " + orderId));
        orderToUpdate.setCreateDate(LocalDateTime.now());
        orderToUpdate.setTotalPrice(updateOrder.getTotalPrice());

        return orderRepository.save(orderToUpdate);
    }


    @Override
    public List<Order> getAll() {
        return orderRepository.findAll();
    }

    @Override
    public void delete(Long orderId) {
        orderRepository.deleteById(orderId);
    }

    @Override
    public Order getById(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found with id: " + orderId));
        return order;
    }

    @Override
    public List<Order> getOrdersAfterDate(LocalDateTime date) {
        return orderRepository.findByCreateDateAfter(date);
    }
}
