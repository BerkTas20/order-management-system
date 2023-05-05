package com.berktas.ordermanagementsystem.service.impl;

import com.berktas.ordermanagementsystem.controller.requests.SaveAndUpdateCustomerRequest;
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
import java.util.Map;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(JUnit4.class)
public class CustomerServiceImplTest {
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
    public void testCreateCustomer() {
        SaveAndUpdateCustomerRequest createCustomerRequest = new SaveAndUpdateCustomerRequest();
        createCustomerRequest.setName("test customer");
        createCustomerRequest.setAge(30);

        Customer expectedCustomer = new Customer();
        expectedCustomer.setId(1L);
        expectedCustomer.setName("test customer");
        expectedCustomer.setAge(30);

        when(customerRepositoryMock.save(any(Customer.class))).thenReturn(expectedCustomer);

        Customer actualCustomer = customerService.create(createCustomerRequest);

        verify(customerRepositoryMock, times(1)).save(any(Customer.class));

        assertEquals(expectedCustomer, actualCustomer);
    }

    @Test
    public void testUpdateCustomer() {
        Long customerId = 1L;
        SaveAndUpdateCustomerRequest updateCustomerRequest = new SaveAndUpdateCustomerRequest();
        updateCustomerRequest.setName("updated customer");
        updateCustomerRequest.setAge(35);

        Customer existingCustomer = new Customer();
        existingCustomer.setId(customerId);
        existingCustomer.setName("test customer");
        existingCustomer.setAge(30);

        Customer updatedCustomer = new Customer();
        updatedCustomer.setId(customerId);
        updatedCustomer.setName("updated customer");
        updatedCustomer.setAge(35);

        when(customerRepositoryMock.findById(customerId)).thenReturn(Optional.of(existingCustomer));
        when(customerRepositoryMock.save(existingCustomer)).thenReturn(updatedCustomer);

        Customer actualCustomer = customerService.update(customerId, updateCustomerRequest);

        verify(customerRepositoryMock, times(1)).findById(customerId);
        verify(customerRepositoryMock, times(1)).save(existingCustomer);

        assertEquals(updatedCustomer, actualCustomer);
    }

    @Test
    public void testGetAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        customers.add(Customer.builder()
                .name("John Doe")
                .age(30)
                .build());
        customers.add(Customer.builder()
                .name("Jane Doe")
                .age(25)
                .build());

        when(customerRepositoryMock.findAll()).thenReturn(customers);

        List<Customer> result = customerService.getAll();

        assertEquals(customers, result);
    }

    @Test
    public void testDeleteCustomer() {
        Long customerId = 1L;

        customerService.delete(customerId);

        verify(customerRepositoryMock, times(1)).deleteById(customerId);
    }

    @Test
    public void testGetCustomerById() {
        Long customerId = 1L;

        Customer expectedCustomer = new Customer();
        expectedCustomer.setId(customerId);
        expectedCustomer.setName("test customer");
        expectedCustomer.setAge(30);

        when(customerRepositoryMock.findById(customerId)).thenReturn(Optional.of(expectedCustomer));

        Customer actualCustomer = customerService.getById(customerId);

        verify(customerRepositoryMock, times(1)).findById(customerId);

        assertEquals(expectedCustomer, actualCustomer);
    }

    @Test
    public void testSearchCustomersAndOrdersByKeyword_withOneCustomerOneOrder() {
        // Given
        String keyword = "test";

        Customer customer1 = Customer.builder()
                .name("test customer1")
                .age(20)
                .build();
        List<Customer> customers = new ArrayList<>();
        customers.add(customer1);

        Order order1 = Order.builder()
                .createDate(LocalDateTime.now())
                .totalPrice(50).build();
        order1.setCustomer(customer1);
        List<Order> orders = new ArrayList<>();
        orders.add(order1);

        OrderRepository orderRepository = mock(OrderRepository.class);
        when(orderRepository.findByCustomerIn(customers)).thenReturn(orders);

        CustomerRepository customerRepositoryMock = mock(CustomerRepository.class);
        when(customerRepositoryMock.findByNameContaining(keyword)).thenReturn(customers);

        CustomerService customerService = new CustomerServiceImpl(customerRepositoryMock, orderRepository);

        Map<String, List<Long>> searchResult = customerService.searchCustomersAndOrdersByKeyword(keyword);

        assertTrue(searchResult.containsKey(customer1.getName()));
    }

    @Test
    public void testSearchCustomersAndOrdersByKeyword_withNoMatchingOrders() {
        String keyword = "test";
        Customer customer1 = Customer.builder()
                .name("test customer1")
                .age(20)
                .build();
        List<Customer> customers = new ArrayList<>();
        customers.add(customer1);

        when(customerRepositoryMock.findByNameContaining(keyword)).thenReturn(customers);
        CustomerRepository customerRepositoryMock = mock(CustomerRepository.class);
        when(customerRepositoryMock.findByNameContaining(keyword)).thenReturn(customers);

        CustomerService customerService = new CustomerServiceImpl(customerRepositoryMock, orderRepositoryMock);


        Map<String, List<Long>> searchResult = customerService.searchCustomersAndOrdersByKeyword(keyword);

        assertEquals(1, searchResult.size());
        assertTrue(searchResult.containsKey(customer1.getName()));
        assertEquals(0, searchResult.get(customer1.getName()).size());
    }
}

