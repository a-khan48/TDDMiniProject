package com.example.TDDminiproj.repository;

import com.example.TDDminiproj.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.Optional;

@DataJpaTest
public class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    // Test case to save an order and verify if it is successfully saved
    @Test
    public void testSaveOrder() {
        Order order = new Order("Test Name", LocalDate.now(), "123 Main st", 50.0);

        Order savedOrder = orderRepository.save(order);
        Assertions.assertNotNull(savedOrder.getId());
    }

    // Test case to find an order by its ID and verify if it exists and has the expected customer name
    @Test
    public void testFindOrderById() {
        Order order = new Order("Test Name", LocalDate.now(), "123 Main st", 50.0);
        orderRepository.save(order);

        Order foundOrder = orderRepository.findById(order.getId()).orElse(null);
        Assertions.assertNotNull(foundOrder);
        Assertions.assertEquals(order.getCustomerName(), foundOrder.getCustomerName());
    }

    // Test case to update an order and verify if the changes are persisted correctly
    @Test
    public void testUpdateOrder() {
        Order order = new Order("Test Name", LocalDate.now(), "123 Main st", 50.0);
        orderRepository.save(order);

        // Modify the order
        order.setCustomerName("John Doe");
        order.setTotal(100.0);

        Order updatedOrder = orderRepository.save(order);
        Assertions.assertEquals("John Doe", updatedOrder.getCustomerName());
        Assertions.assertEquals(100.0, updatedOrder.getTotal());
    }

    // Test case to delete an order and verify if it is successfully deleted
    @Test
    public void testDeleteOrder() {
        Order order = new Order("Test Name", LocalDate.now(), "123 Main st", 50.0);
        orderRepository.save(order);

        orderRepository.delete(order);

        Optional<Order> optionalOrder = orderRepository.findById(order.getId());
        Assertions.assertFalse(optionalOrder.isPresent());
    }
}
