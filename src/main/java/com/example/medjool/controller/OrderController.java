package com.example.medjool.controller;

import com.example.medjool.dto.OrderDto;
import com.example.medjool.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        return orderService.processOrder(orderDto);
    }

    @GetMapping("/get_all")
    public ResponseEntity<?> getAll(){
        return orderService.getAllOrders();
    }

}
