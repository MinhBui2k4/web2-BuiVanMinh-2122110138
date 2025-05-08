package com.example.BuiVanMinh.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.BuiVanMinh.domain.OrderDetail;
import com.example.BuiVanMinh.service.OrderDetailService;

@RestController
@RequestMapping("/api/order-details")
@CrossOrigin(origins = "*") // Cho phép frontend gọi API
public class OrderDetailController {

    private static final Logger logger = LoggerFactory.getLogger(OrderDetailController.class);

    @Autowired
    private OrderDetailService orderDetailService;

    @GetMapping
    public List<OrderDetail> getAll() {
        return orderDetailService.getAllOrderDetails();
    }

    @GetMapping("/{id}")
    public OrderDetail getById(@PathVariable int id) {
        return orderDetailService.getOrderDetailById(id);
    }

    @PostMapping
    public OrderDetail create(@RequestBody OrderDetail orderDetail) {
        return orderDetailService.createOrderDetail(orderDetail);
    }

    @PutMapping("/{id}")
    public OrderDetail update(@PathVariable int id, @RequestBody OrderDetail orderDetail) {
        return orderDetailService.updateOrderDetail(id, orderDetail);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        orderDetailService.deleteOrderDetail(id);
    }
    @GetMapping("/order/{orderId}")
    public ResponseEntity<?> getOrderDetailsByOrderId(@PathVariable Long orderId) {
        logger.info("Fetching order details for order: {}", orderId);
        try {
            List<OrderDetail> orderDetails = orderDetailService.getOrderDetailsByOrderId(orderId);
            return ResponseEntity.ok(orderDetails);
        } catch (Exception e) {
            logger.error("Error fetching order details for order {}: {}", orderId, e.getMessage());
            return ResponseEntity.status(500).body("Error fetching order details: " + e.getMessage());
        }
    }
}
