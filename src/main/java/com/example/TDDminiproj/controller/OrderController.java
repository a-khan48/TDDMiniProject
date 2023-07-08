package com.example.TDDminiproj.controller;

import com.example.TDDminiproj.model.Order;
import com.example.TDDminiproj.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderRepository orderRepository;

    @Autowired
    public OrderController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    // Create a new order
    @PostMapping
    public ResponseEntity<?> createOrder(@Valid @RequestBody Order order, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // Collect validation error messages and return a bad request response
            List<String> errors = bindingResult.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errors);
        }

        // Save the order and return a created response with the saved order
        Order savedOrder = orderRepository.save(order);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedOrder);
    }

    // Get all orders
    @GetMapping
    public ResponseEntity<List<Order>> getOrders() {
        // Retrieve all orders from the repository and return them in a response
        List<Order> orders = orderRepository.findAll();
        return ResponseEntity.ok(orders);
    }

    // Get an order by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable Long id) {
        return orderRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Update an existing order
    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrder(@PathVariable Long id, @Valid @RequestBody Order updatedOrder, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // Collect validation error messages and return a bad request response
            List<String> errors = bindingResult.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errors);
        }

        return orderRepository.findById(id)
                .map(existingOrder -> {
                    // Update the existing order with the new values and save it
                    existingOrder.setCustomerName(updatedOrder.getCustomerName());
                    existingOrder.setOrderDate(updatedOrder.getOrderDate());
                    existingOrder.setShippingAddress(updatedOrder.getShippingAddress());
                    existingOrder.setTotal(updatedOrder.getTotal());
                    orderRepository.save(existingOrder);
                    // Return the updated order in a response
                    return ResponseEntity.ok(existingOrder);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Delete an order by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if (optionalOrder.isPresent()) {
            Order existingOrder = optionalOrder.get();
            // Delete the existing order and return a no content response
            orderRepository.delete(existingOrder);
            return ResponseEntity.noContent().build();
        } else {
            // Return a not found response if the order doesn't exist
            return ResponseEntity.notFound().build();
        }
    }
}
