package com.example.medjool.services;
import com.example.medjool.dto.OrderRequestDto;
import com.example.medjool.dto.OrderResponseDto;
import com.example.medjool.dto.OrderStatusDto;
import com.example.medjool.dto.OrderUpdateRequestDto;
import org.springframework.http.ResponseEntity;

import java.util.List;


public interface OrderService {

    ResponseEntity<?> createOrder(OrderRequestDto orderDto) throws Exception;
    List<OrderResponseDto> getAllOrders();
    OrderResponseDto getOrderById(Long id);
    ResponseEntity<Object> updateOrder(Long id, OrderUpdateRequestDto orderUpdateRequestDto);
    ResponseEntity<Object> updateOrderStatus(Long id, OrderStatusDto orderStatusDto) throws Exception;
    ResponseEntity<Object> cancelOrder(Long id);


}