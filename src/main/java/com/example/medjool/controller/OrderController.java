package com.example.medjool.controller;

import com.example.medjool.dto.OrderRequestDto;
import com.example.medjool.dto.OrderResponseDto;

import com.example.medjool.dto.OrderStatusDto;
import com.example.medjool.services.implementation.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/order")
public class OrderController {

    private final OrderServiceImpl orderService;

    @Autowired
    public OrderController(OrderServiceImpl orderService) {
        this.orderService = orderService;
    }


    @PostMapping("")
    public OrderResponseDto makeOrder(@RequestBody OrderRequestDto orderRequestDto) {
        return orderService.createOrder(orderRequestDto);
    }

    @GetMapping("/get_all")
    public List<OrderResponseDto> getAll(){
        return orderService.getAllOrders();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateOrder(@PathVariable Long id, @RequestBody OrderStatusDto orderStatusDto) {
        return orderService.updateOrderStatus(id,orderStatusDto);
    }

    @DeleteMapping("/cancel/{id}")
    public ResponseEntity<Object> cancelOrder(@PathVariable Long id) {
        return orderService.cancelOrder(id);
    }

}
