package com.example.medjool.services;

import com.example.medjool.dto.OrderItemRequestDto;
import com.example.medjool.dto.OrderRequestDto;
import com.example.medjool.dto.OrderResponseDto;
import com.example.medjool.dto.OrderStatusDto;
import com.example.medjool.exception.ProductLowStock;
import com.example.medjool.exception.ProductNotFoundException;
import com.example.medjool.model.*;
import com.example.medjool.repository.ClientRepository;
import com.example.medjool.repository.OrderRepository;
import com.example.medjool.repository.PalletRepository;
import com.example.medjool.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final ClientRepository clientRepository;
    private final PalletRepository palletRepository;

    Logger logger = Logger.getLogger(OrderService.class.getName());

    @Transactional
    public OrderResponseDto createOrder(OrderRequestDto orderRequest) {

        // Create new order object
        Order order = new Order();
        LocalDate orderDate = LocalDate.now();

        // Find the corresponding client
        Client client = clientRepository.findByCompanyName(orderRequest.getClientName());
        order.setClient(client);

        // Initiate the total price;
        double totalPrice = 0;

        // Initiate the total weight:
        double totalWeight = 0;


        logger.info("New Order is being processed...");
        // Iterate over each order item
        for (OrderItemRequestDto itemRequest : orderRequest.getItems()) {
            Product product = productRepository.findByCallibreAndColorAndQuality(itemRequest.getCallibre(),itemRequest.getColor(),itemRequest.getQuality());

            Optional<Pallet> pallet = palletRepository.findById(itemRequest.getPalletId());
            // Skip if product not found or insufficient stock
            if (product == null){
                throw new ProductNotFoundException();
            }
            else if (product.getTotalWeight() < itemRequest.getItemWeight()){
                throw new ProductLowStock();
            }
            // Update product inventory

            logger.info("The product with id : " + product.getProductId() + "is being updated...");
            product.setTotalWeight(product.getTotalWeight() - itemRequest.getItemWeight());
            productRepository.save(product);

            // Create order item
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setPricePerKg(itemRequest.getPricePerKg());
            orderItem.setPackaging(itemRequest.getPackaging());
            orderItem.setNumberOfPallets(itemRequest.getNumberOfPallets());
            orderItem.setItemWeight(itemRequest.getItemWeight());
            orderItem.setPallet(pallet.get());

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
            order.setStatus(OrderStatus.valueOf("PRELIMINARY"));
            order.setDate(orderDate);
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



    @Transactional
    public ResponseEntity<Object> updateOrderStatus(Long id, OrderStatusDto orderStatusDto){
        Order order = orderRepository.findById(id).orElse(null);
        if (order == null) {
            return ResponseEntity.notFound().build();

        }
        order.setStatus(OrderStatus.valueOf(orderStatusDto.getNewStatus()));
        orderRepository.save(order);
        return ResponseEntity.ok().build();
    }

    @Transactional
    public ResponseEntity<Object> cancelOrder(Long id) {
        Order order = orderRepository.findById(id).orElse(null);

        if (order == null) {
            return ResponseEntity.notFound().build();
        }

        if (order.getStatus() == OrderStatus.IN_PRODUCTION) {
            return new ResponseEntity<>("Order is already in production and cannot be canceled at the moment...", HttpStatus.CONFLICT);
        }

        for (OrderItem orderItem : order.getOrderItems()) {
            Product product = orderItem.getProduct();
            // Assuming getWeight() or quantity is the correct field to restore stock
            product.setTotalWeight(product.getTotalWeight() + orderItem.getItemWeight());
            // No need to call productRepository.save(product); if within @Transactional and managed context
        }

        order.setStatus(OrderStatus.CANCELED);
        // No need to call orderRepository.save(order); for same reason

        return new ResponseEntity<>("Order has been cancelled.", HttpStatus.OK);
    }

}