package com.berktas.ordermanagementsystem.controller;

import com.berktas.ordermanagementsystem.controller.requests.SaveAndUpdateOrderRequest;
import com.berktas.ordermanagementsystem.model.entity.Order;
import com.berktas.ordermanagementsystem.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;


@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
@Tag(name = "Order")
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/{customerId}")
    @Operation(summary = "Create Order With Customer Id")
    public ResponseEntity<Order> createOrder(@RequestBody SaveAndUpdateOrderRequest createOrder, @PathVariable Long customerId) {
        return ResponseEntity.ok(orderService.create(createOrder, customerId));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Order")
    public ResponseEntity<Order> updateOrder(@PathVariable Long id, @RequestBody SaveAndUpdateOrderRequest updateOrder) {
        return ResponseEntity.ok(orderService.update(id, updateOrder));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Order")
    public void deleteOrder(@PathVariable Long id) {
        orderService.delete(id);
    }

    @GetMapping
    @Operation(summary = "Get All Orders")
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Order By Id")
    public ResponseEntity<Order> getOrderById(Long orderId) {
        return ResponseEntity.ok(orderService.getById(orderId));
    }

    @GetMapping("/afterDate")
    @Operation(summary = "Get Orders After Date")
    public ResponseEntity<List<Order>> getOrdersAfterDate(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date) {
        return ResponseEntity.ok(orderService.getOrdersAfterDate(date));
    }
}
