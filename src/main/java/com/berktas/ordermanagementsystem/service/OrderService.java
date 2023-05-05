package com.berktas.ordermanagementsystem.service;

import com.berktas.ordermanagementsystem.controller.requests.SaveAndUpdateOrderRequest;
import com.berktas.ordermanagementsystem.model.entity.Order;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderService {

    Order create(SaveAndUpdateOrderRequest createOrder, Long customerId);

    Order update(Long orderId, SaveAndUpdateOrderRequest updateOrder);

    List<Order> getAll();

    void delete(Long orderId);

    Order getById(Long orderId);

    List<Order> getOrdersAfterDate(LocalDateTime date);

}
