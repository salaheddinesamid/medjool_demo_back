package com.example.medjool.repository;

import com.example.medjool.model.OrderHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderHistoryRepository extends JpaRepository<OrderHistory, Long> {
    OrderHistory findByOrderId(Long orderId);
}
