package com.example.BuiVanMinh.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.BuiVanMinh.domain.OrderDetail;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {
    List<OrderDetail> findByOrderId(Long orderId);
}
