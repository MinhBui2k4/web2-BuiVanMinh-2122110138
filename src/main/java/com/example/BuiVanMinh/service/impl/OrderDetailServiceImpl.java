package com.example.BuiVanMinh.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.BuiVanMinh.domain.OrderDetail;
import com.example.BuiVanMinh.repository.OrderDetailRepository;
import com.example.BuiVanMinh.service.OrderDetailService;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {

    private static final Logger logger = LoggerFactory.getLogger(OrderDetailServiceImpl.class);
    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Override
    public List<OrderDetail> getAllOrderDetails() {
        return orderDetailRepository.findAll();
    }

    @Override
    public OrderDetail getOrderDetailById(int id) {
        return orderDetailRepository.findById(id).orElse(null);
    }

    @Override
    public OrderDetail createOrderDetail(OrderDetail orderDetail) {
        return orderDetailRepository.save(orderDetail);
    }

    @Override
    public OrderDetail updateOrderDetail(int id, OrderDetail orderDetail) {
        return orderDetailRepository.findById(id).map(existing -> {
            existing.setOrder(orderDetail.getOrder());
            existing.setProduct(orderDetail.getProduct());
            existing.setQuantity(orderDetail.getQuantity());
            existing.setPrice(orderDetail.getPrice());
            return orderDetailRepository.save(existing);
        }).orElseThrow(() -> new RuntimeException("OrderDetail not found"));
    }

    @Override
    public void deleteOrderDetail(int id) {
        orderDetailRepository.deleteById(id);
    }
    @Override
    public List<OrderDetail> getOrderDetailsByOrderId(Long orderId) {
        logger.info("Fetching order details for order: {}", orderId);
        return orderDetailRepository.findByOrderId(orderId);
    }
}
