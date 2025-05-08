package com.example.BuiVanMinh.controller;

import com.example.BuiVanMinh.domain.Order;
import com.example.BuiVanMinh.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "*") // Cho phép frontend gọi API
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/{id}")
    public Order getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }

    @PostMapping
    public Order createOrder(@RequestBody Order order) {
        return orderService.saveOrder(order);
    }

    @PutMapping("/{id}")
    public Order updateOrder(@PathVariable Long id, @RequestBody Order updatedOrder) {
        Order existingOrder = orderService.getOrderById(id);
        if (existingOrder != null) {
            existingOrder.setProductId(updatedOrder.getProductId());
            existingOrder.setName(updatedOrder.getName());
            existingOrder.setTotalPrice(updatedOrder.getTotalPrice());
            existingOrder.setUser(updatedOrder.getUser());
            return orderService.saveOrder(existingOrder);
        } else {
            return null;
        }
    }

    @GetMapping("/user/{userId}")
    public List<Order> getOrdersByUserId(@PathVariable Long userId) {
        return orderService.getOrdersByUserId(userId);
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
    }
}