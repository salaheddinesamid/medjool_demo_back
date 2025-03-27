package com.example.medjool.controller;

import com.example.medjool.dto.OrderDto;
import com.example.medjool.dto.OrderStatusDto;
import com.example.medjool.model.Order;
import com.example.medjool.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/order")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }


    @PostMapping("")
    public ResponseEntity<?> makeOrder(@RequestBody OrderDto orderDto){
        return orderService.createOrder(orderDto);
    }

    @GetMapping("/get_all")
    public List<Order> getAll(){
        return orderService.getAllOrders();
    }

    @PutMapping("update/{orderId}")
    public ResponseEntity<?> updateOrderStatus(@PathVariable Long orderId, @RequestBody OrderStatusDto newStatus){

        return orderService.updateOrderStatus(orderId, newStatus);
    }


    @DeleteMapping("cancel/{orderId}")
    public ResponseEntity<?> cancelOrder(@PathVariable Long orderId){
        return orderService.cancelOrders(orderId);
    }
}
