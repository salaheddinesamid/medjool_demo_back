package com.example.medjool.services;
import com.example.medjool.dto.OrderRequestDto;
import com.example.medjool.dto.OrderResponseDto;
import com.example.medjool.dto.OrderStatusDto;
import org.springframework.http.ResponseEntity;

import java.util.List;


public interface OrderService {

    OrderResponseDto createOrder(OrderRequestDto orderDto);
    List<OrderResponseDto> getAllOrders();
    OrderResponseDto getOrderById(Long id);
    ResponseEntity<Object> updateOrderStatus(Long id, OrderStatusDto orderStatusDto);
    ResponseEntity<Object> cancelOrder(Long id);


}