package com.berktas.ordermanagementsystem.service.impl;

import com.berktas.ordermanagementsystem.controller.requests.SaveAndUpdateOrderRequest;
import com.berktas.ordermanagementsystem.model.entity.Customer;
import com.berktas.ordermanagementsystem.model.entity.Order;
import com.berktas.ordermanagementsystem.repository.CustomerRepository;
import com.berktas.ordermanagementsystem.repository.OrderRepository;
import com.berktas.ordermanagementsystem.service.CustomerService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(JUnit4.class)
public class OrderServiceImplTest {
    private CustomerRepository customerRepositoryMock;
    @Mock
    private OrderRepository orderRepositoryMock;
    private CustomerService customerService;

    @InjectMocks
    private OrderServiceImpl orderService;

    @InjectMocks
    private CustomerServiceImpl service;

    @Before
    public void setUp() {
        customerRepositoryMock = mock(CustomerRepository.class);
        customerService = new CustomerServiceImpl(customerRepositoryMock, orderRepositoryMock);
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateOrder() {
        // Given
        SaveAndUpdateOrderRequest createOrder = new SaveAndUpdateOrderRequest();
        createOrder.setTotalPrice(Double.valueOf(100));
        Long customerId = 1L;

        Customer customer = Customer.builder()
                .name("Test Customer")
                .age(30)
                .build();

        when(customerRepositoryMock.findById(customerId)).thenReturn(Optional.of(customer));


    }
    @Test
    public void testCreateOrderWithInvalidCustomerId() {
        // Given
        SaveAndUpdateOrderRequest createOrder = new SaveAndUpdateOrderRequest();
        createOrder.setTotalPrice((100));
        Long customerId = 1L;

        when(customerRepositoryMock.findById(customerId)).thenReturn(Optional.empty());
    }

    @Test
    public void testUpdateOrder_Success() {
        // Given
        Long orderId = 1L;
        SaveAndUpdateOrderRequest updateOrder = new SaveAndUpdateOrderRequest();
        updateOrder.setTotalPrice(150.0);
        updateOrder.setCreateDate(LocalDateTime.now());

        Order orderToUpdate = Order.builder()
                .createDate(LocalDateTime.now().minusDays(1))
                .totalPrice(100.0)
                .build();

        when(orderRepositoryMock.findById(orderId)).thenReturn(Optional.of(orderToUpdate));
        when(orderRepositoryMock.save(orderToUpdate)).thenReturn(orderToUpdate);

        // When
        Order updatedOrder = orderService.update(orderId, updateOrder);

        // Then
        assertNotNull(updatedOrder);
        assertEquals(updateOrder.getTotalPrice(), updatedOrder.getTotalPrice(), 0.001);
        assertNotNull(updatedOrder.getCreateDate());
    }

    @Test
    public void testDeleteOrder() {
        Long orderId = 1L;

        orderService.delete(orderId);

        verify(orderRepositoryMock, times(1)).deleteById(orderId);
    }

    @Test
    public void testGetAllCustomers() {
        List<Order> orders = new ArrayList<>();
        orders.add(Order.builder()
                        .totalPrice(60)
                .createDate(LocalDateTime.now())
                .build());
        orders.add(Order.builder()
                .totalPrice(96)
                .createDate(LocalDateTime.now())
                .build());

        when(orderRepositoryMock.findAll()).thenReturn(orders);

        List<Order> result = orderService.getAll();

        assertEquals(orders, result);
    }

    @Test
    public void testGetCustomerById() {
        Long orderId = 1L;

        Order expectedOrder = new Order();
        expectedOrder.setId(orderId);
        expectedOrder.setTotalPrice(1000);


        when(orderRepositoryMock.findById(orderId)).thenReturn(Optional.of(expectedOrder));

        Order actualOrder = orderService.getById(orderId);

        verify(orderRepositoryMock, times(1)).findById(orderId);

        assertEquals(expectedOrder, actualOrder);
    }
}