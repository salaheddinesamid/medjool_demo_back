package com.example.medjool.services;

import com.example.medjool.dto.OrderItemRequestDto;
import com.example.medjool.dto.OrderRequestDto;
import com.example.medjool.dto.OrderResponseDto;
import com.example.medjool.dto.OrderStatusDto;
import com.example.medjool.model.*;
import com.example.medjool.repository.ClientRepository;
import com.example.medjool.repository.OrderRepository;
import com.example.medjool.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final ClientRepository clientRepository;

    @Transactional
    public OrderResponseDto createOrder(OrderRequestDto orderRequest) {

        // Create new order object
        Order order = new Order();

        // Find the corresponding client
        Client client = clientRepository.findByCompanyName(orderRequest.getClientName());
        order.setClient(client);

        // Initiate the total price;
        double totalPrice = 0;

        // Initiate the total weight:
        double totalWeight = 0;

        // Iterate over each order item
        for (OrderItemRequestDto itemRequest : orderRequest.getItems()) {
            Product product = productRepository.findById(itemRequest.getProductId()).orElse(null);

            // Skip if product not found or insufficient stock
            if (product == null || product.getTotalWeight() < itemRequest.getItemWeight()) {
                continue;
            }

            // Update product inventory
            product.setTotalWeight(product.getTotalWeight() - itemRequest.getItemWeight());
            productRepository.save(product);

            // Create order item
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setPricePerKg(itemRequest.getPricePerKg());
            orderItem.setPackaging(itemRequest.getPackaging());
            orderItem.setNumberOfPallets(itemRequest.getNumberOfPallets());
            orderItem.setItemWeight(itemRequest.getItemWeight());

            // Calculate totals
            double itemPrice = itemRequest.getPricePerKg() * itemRequest.getItemWeight();
            totalPrice += itemPrice;
            totalWeight += itemRequest.getItemWeight();

            order.addOrderItem(orderItem);
        }

        // Only save if we have valid items
        if (!order.getOrderItems().isEmpty()) {
            order.setTotalPrice(totalPrice);
            order.setTotalWeight(totalWeight);
            Order savedOrder = orderRepository.save(order);
            return new OrderResponseDto(savedOrder);
        }

        return null; // Or return empty order response
    }

    @Transactional(readOnly = true)
    public List<OrderResponseDto> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(OrderResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public OrderResponseDto getOrderById(Long id) {
        return orderRepository.findById(id)
                .map(OrderResponseDto::new)
                .orElse(null);
    }

    public ResponseEntity<Object> updateOrderStatus(Long id, OrderStatusDto orderStatusDto){
        Order order = orderRepository.findById(id).orElse(null);
        if (order == null) {
            return ResponseEntity.notFound().build();

        }
        order.setStatus(OrderStatus.valueOf(orderStatusDto.getNewStatus()));
        orderRepository.save(order);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Object> cancelOrder(Long id) {
        Order order = orderRepository.findById(id).orElse(null);

        if (order == null) {
            return ResponseEntity.notFound().build();
        }
        else if (order.getStatus().toString().equals("IN_PRODUCTION")){
            return new ResponseEntity<>("Order is already in production and can not be canceled at the moment...", HttpStatus.CONFLICT);
        }

        order.setStatus(OrderStatus.valueOf("CANCELLED"));
        orderRepository.save(order);

        return new ResponseEntity<>("Order has been cancelled.", HttpStatus.OK);
    }
}