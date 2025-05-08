package com.example.BuiVanMinh.service;

import java.util.List;

import com.example.BuiVanMinh.domain.OrderDetail;

public interface OrderDetailService {
    List<OrderDetail> getAllOrderDetails();
    OrderDetail getOrderDetailById(int id);
    List<OrderDetail> getOrderDetailsByOrderId(Long orderId);
    OrderDetail createOrderDetail(OrderDetail orderDetail);
    OrderDetail updateOrderDetail(int id, OrderDetail orderDetail);
    void deleteOrderDetail(int id);
}
