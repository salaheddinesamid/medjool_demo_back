package com.example.medjool.controller;

import com.example.medjool.dto.*;

import com.example.medjool.model.OrderHistory;
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
    public ResponseEntity<?> makeOrder(@RequestBody OrderRequestDto orderRequestDto) throws Exception {
        return orderService.createOrder(orderRequestDto);
    }


    @GetMapping("/get_all")
    public List<OrderResponseDto> getAll(){
        return orderService.getAllOrders();
    }

    @PutMapping("status/update/{id}")
    public ResponseEntity<Object> updateOrder(@PathVariable Long id, @RequestBody OrderStatusDto orderStatusDto) throws Exception {
        return orderService.updateOrderStatus(id,orderStatusDto);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateOrder(@PathVariable Long id, @RequestBody OrderUpdateRequestDto orderRequestDto) throws Exception {
        return orderService.updateOrder(id,orderRequestDto);
    }

    @GetMapping("history/get_all")

    public ResponseEntity<List<OrderHistoryResponseDto>> getAllHistory(){
        return orderService.getAllOrderHistory();
    }

    @DeleteMapping("/cancel/{id}")
    public ResponseEntity<Object> cancelOrder(@PathVariable Long id) {
        return orderService.cancelOrder(id);
    }

}
