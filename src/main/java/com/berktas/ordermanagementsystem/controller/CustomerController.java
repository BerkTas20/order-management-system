package com.berktas.ordermanagementsystem.controller;

import com.berktas.ordermanagementsystem.controller.requests.SaveAndUpdateCustomerRequest;
import com.berktas.ordermanagementsystem.model.entity.Customer;
import com.berktas.ordermanagementsystem.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
@Tag(name = "Customer")
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping
    @Operation(summary = "Create Customer")
    public ResponseEntity<Customer> createCustomer(@RequestBody SaveAndUpdateCustomerRequest createCustomer) {
        return ResponseEntity.ok(customerService.create(createCustomer));
    }

    @PutMapping
    @Operation(summary = "Update Customer")
    public ResponseEntity<Customer> updateCustomer(@RequestParam Long id, @RequestBody SaveAndUpdateCustomerRequest updateCustomer) {
        return ResponseEntity.ok(customerService.update(id, updateCustomer));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Customer")
    public void deleteCustomer(@PathVariable Long id) {
        customerService.delete(id);
    }

    @GetMapping
    @Operation(summary = "Get All Customers")
    public ResponseEntity<List<Customer>> getAllCustomers() {
        return ResponseEntity.ok(customerService.getAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Customer By Id")
    public ResponseEntity<Customer> getCustomerById(Long customerId) {
        return ResponseEntity.ok(customerService.getById(customerId));
    }


    @GetMapping("/search/{keyword}")
    @Operation(summary = "Get Customer And Customer's Order Id")
    public Map<String, List<Long>> searchCustomersAndOrders(@PathVariable String keyword) {
        return customerService.searchCustomersAndOrdersByKeyword(keyword);
    }

    @GetMapping("/without-orders")
    @Operation(summary = "List Customers Without Orders")
    public ResponseEntity<List<Customer>> findCustomersWithoutOrders() {
        return ResponseEntity.ok(customerService.findCustomersWithoutOrders());
    }
}
