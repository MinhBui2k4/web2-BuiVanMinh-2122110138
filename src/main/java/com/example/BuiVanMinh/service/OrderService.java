package com.example.BuiVanMinh.service;

import com.example.BuiVanMinh.domain.Order;

import java.util.List;

public interface OrderService {
    List<Order> getOrdersByUserId(Long userId);

    List<Order> getAllOrders();

    Order getOrderById(Long id);

    Order saveOrder(Order order);

    void deleteOrder(Long id);
}