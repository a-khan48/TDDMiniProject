package com.example.TDDminiproj.controller;

import com.example.TDDminiproj.model.Order;
import com.example.TDDminiproj.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Arrays;

// This is a test class for the OrderController class. It uses the WebMvcTest annotation
// to enable Spring MVC testing for the OrderController. It tests various methods of the
// OrderController using Mockito mocks and performs HTTP requests using MockMvc.
@WebMvcTest(OrderController.class)
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderRepository orderRepository;

    private Order order;

    // This method is executed before each test case. It sets up the test data.
    @BeforeEach
    public void setup() {
        order = new Order("Test Name", LocalDate.now(), "123 Main st", 50.0);
    }

    // This test case tests the createOrder() method of the OrderController.
    // It verifies that a POST request to "/orders" with valid JSON data returns a
    // HTTP status of 201 (Created) and the response JSON contains the expected customerName.
    @Test
    public void testCreateOrder() throws Exception {
        String orderJson = "{ \"customerName\": \"Test Name\", \"orderDate\": \"2023-07-06\", \"shippingAddress\": \"123 Main St\", \"total\": 50.0 }";

        Mockito.when(orderRepository.save(Mockito.any(Order.class))).thenReturn(order);

        mockMvc.perform(MockMvcRequestBuilders.post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(orderJson))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.customerName").value("Test Name"));
    }

    // This test case tests the getOrders() method of the OrderController.
    // It verifies that a GET request to "/orders" returns a HTTP status of 200 (OK)
    // and the response JSON contains the expected customerName.
    @Test
    public void testGetOrders() throws Exception {
        Mockito.when(orderRepository.findAll()).thenReturn(Arrays.asList(order));

        mockMvc.perform(MockMvcRequestBuilders.get("/orders")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].customerName").value("Test Name"));
    }

    // This test case tests the getOrderById() method of the OrderController.
    // It verifies that a GET request to "/orders/{id}" with a valid orderId returns
    // a HTTP status of 200 (OK) and the response JSON contains the expected customerName.
    @Test
    public void testGetOrderById() throws Exception {
        Long orderId = 1L;

        Mockito.when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        mockMvc.perform(MockMvcRequestBuilders.get("/orders/{id}", orderId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.customerName").value("Test Name"));
    }

    // This test case tests the updateOrder() method of the OrderController.
    // It verifies that a PUT request to "/orders/{id}" with a valid orderId and updated JSON data
    // returns a HTTP status of 200 (OK) and the response JSON contains the updated customerName and total.
    @Test
    public void testUpdateOrder() throws Exception {
        Long orderId = 1L;
        String updatedOrderJson = "{ \"customerName\": \"John Doe\", \"orderDate\": \"2023-07-07\", \"shippingAddress\": \"999 Second st\", \"total\": 100.0 }";

        Mockito.when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        Mockito.when(orderRepository.save(Mockito.any(Order.class))).thenReturn(order);

        mockMvc.perform(MockMvcRequestBuilders.put("/orders/{id}", orderId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedOrderJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.customerName").value("John Doe"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.total").value(100.0));
    }

    // This test case tests the deleteOrder() method of the OrderController.
    // It verifies that a DELETE request to "/orders/{id}" with a valid orderId returns
    // a HTTP status of 204 (No Content).
    @Test
    public void testDeleteOrder() throws Exception {
        Long orderId = 1L;

        Mockito.when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        mockMvc.perform(MockMvcRequestBuilders.delete("/orders/{id}", orderId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    // This test case tests that a createOrder() request with missing customerName field
    // returns a HTTP status of 400 (Bad Request).
    @Test
    public void testMissingCustomerNameOrderReturnsBadRequest() throws Exception {
        String orderJson = "{\"orderDate\":\"2023-07-06\",\"shippingAddress\":\"123 Main St\",\"total\":50.0}";

        mockMvc.perform(MockMvcRequestBuilders.post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(orderJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    // This test case tests that a createOrder() request with a negative total value
    // returns a HTTP status of 400 (Bad Request).
    @Test
    public void testNegativeTotalOrderReturnsBadRequest() throws Exception {
        Long orderId = 1L;
        String orderJson = "{\"customerName\":\"John Doe\",\"orderDate\":\"2023-01-01\",\"shippingAddress\":\"123 Main St\",\"total\":-50.0}";

        Mockito.when(orderRepository.findById(orderId)).thenReturn(Optional.of(new Order()));
        mockMvc.perform(MockMvcRequestBuilders.post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(orderJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}
