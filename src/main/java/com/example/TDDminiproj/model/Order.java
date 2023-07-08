package com.example.TDDminiproj.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;

@Entity
@Table(name = "Customer_order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Customer name cannot be empty") // Empty validation constraints for customer name
    @NotNull(message = "Customer name cannot be null") // Null validation constraints for customer name
    private String customerName;

    private LocalDate orderDate;

    @NotEmpty(message = "Shipping address cannot be empty") // Empty validation constraints for shipping address
    private String shippingAddress;

    @Positive(message = "Total cannot be negative") // Validation constraints for total, total cannot be a negative number
    private Double total;

    // Constructors
    public Order() {
    }

    public Order(String customerName, LocalDate orderDate, String shippingAddress, Double total) {
        this.customerName = customerName;
        this.orderDate = orderDate;
        this.shippingAddress = shippingAddress;
        this.total = total;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}
