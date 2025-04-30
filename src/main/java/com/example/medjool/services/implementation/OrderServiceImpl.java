package com.example.medjool.services.implementation;

import com.example.medjool.dto.*;
import com.example.medjool.exception.ClientNotActiveException;

import com.example.medjool.exception.OrderCannotBeCanceledException;
import com.example.medjool.exception.ProductLowStock;

import com.example.medjool.exception.ProductNotFoundException;
import com.example.medjool.model.*;

import com.example.medjool.repository.*;
import com.example.medjool.services.OrderService;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{


    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final ClientRepository clientRepository;
    private final PalletRepository palletRepository;
    private final ShipmentServiceImpl shipmentService;
    private final OrderItemRepository orderItemRepository;

    Logger logger = Logger.getLogger(OrderService.class.getName());

    @Override
    @Transactional
    public ResponseEntity<?> createOrder(OrderRequestDto orderRequest) {
        logger.info("New Order is being processed...");

        Client client = Optional.ofNullable(clientRepository.findByCompanyName(orderRequest.getClientName()))
                .filter(c -> c.getClientStatus().toString().equals("ACTIVE"))
                .orElseThrow(ClientNotActiveException::new);

        Order order = new Order();
        order.setClient(client);

        List<OrderItem> orderItems = orderRequest.getItems().stream().map(item -> {
            Product product = productRepository.findById(
                    item.getProductId()
            ).orElseThrow(() -> new ProductNotFoundException()
            );
            if (product == null || product.getTotalWeight() < item.getItemWeight()) {
                throw product == null ? new ProductNotFoundException() : new ProductLowStock();
            }

            Pallet pallet = palletRepository.findById(item.getPalletId())
                    .orElseThrow(() -> new RuntimeException("Pallet not found"));

            product.setTotalWeight(product.getTotalWeight() - item.getItemWeight());

            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setPricePerKg(item.getPricePerKg());
            orderItem.setPackaging(item.getPackaging());
            orderItem.setOrderCurrency(OrderCurrency.valueOf(orderRequest.getCurrency()));
            orderItem.setNumberOfPallets(item.getNumberOfPallets());
            orderItem.setItemWeight(item.getItemWeight());
            orderItem.setPallet(pallet);

            // 🔥 Associate the order with the order item
            orderItem.setOrder(order);

            return orderItem;
        }).toList();


        productRepository.saveAll(orderItems.stream().map(OrderItem::getProduct).collect(Collectors.toList()));

        double totalPrice = orderItems.stream().map(orderItem -> orderItem.getPricePerKg() * orderItem.getItemWeight()).reduce(0.0, Double::sum);
        double totalWeight = orderItems.stream().map(OrderItem::getItemWeight).reduce(0.0, Double::sum);
        long estimatedDeliveryTime = orderItems.stream().map(item -> item.getPallet().getPreparationTime()).reduce(0.0, Double::sum).longValue();
        order.setTotalPrice(totalPrice);
        order.setTotalWeight(totalWeight);

        order.setOrderItems(orderItems);
        order.setProductionDate(LocalDateTime.now());
        order.setStatus(OrderStatus.PRELIMINARY);
        order.setCurrency(OrderCurrency.valueOf(orderRequest.getCurrency()));
        order.setDeliveryDate(LocalDateTime.now().plusHours(estimatedDeliveryTime));
        Order savedOrder = orderRepository.save(order);

        return new ResponseEntity<>("Order has been created successfully.", HttpStatus.OK);
    }


    @Transactional(readOnly = true)
    @Override
    public List<OrderResponseDto> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(OrderResponseDto::new)
                .collect(Collectors.toList());
    }


    @Transactional(readOnly = true)
    @Override
    public OrderResponseDto getOrderById(Long id) {
        return orderRepository.findById(id)
                .map(OrderResponseDto::new)
                .orElse(null);
    }

    @Override
    @Transactional
    public ResponseEntity<Object> updateOrder(Long id, OrderUpdateRequestDto orderUpdateRequestDto) {
        Optional<Order> order = orderRepository.findById(id);
        order.ifPresent(o -> {
            if(o.getStatus() == OrderStatus.READY_TO_SHIPPED){
                throw new OrderCannotBeCanceledException();
            }

            // Calculate the new delivery date:
            double workingHours = 0;

            for (OrderItemUpdateRequestDto itemRequest : orderUpdateRequestDto.getUpdatedItems()) {
                Product product = productRepository.findById(itemRequest.getProductId()).orElse(null);
                Optional<Pallet> pallet = palletRepository.findById(itemRequest.getNewPalletId());
                // Skip if product not found or insufficient stock
                if (product == null){
                    throw new ProductNotFoundException();
                }
                else if (product.getTotalWeight() < itemRequest.getNewWeight()){
                    throw new ProductLowStock();
                }
                // Update product inventory
                logger.info("The product with id : " + product.getProductId() + "is being updated...");
                product.setTotalWeight(product.getTotalWeight() - itemRequest.getNewWeight());
                productRepository.save(product);

                // Incrementing the working hours:
                workingHours += pallet.get().getPreparationTime();
                // Update order item
                OrderItem orderItem = orderItemRepository.findById(itemRequest.getItemId()).get();
                orderItem.setProduct(product);
                orderItem.setPricePerKg(itemRequest.getNewPrice());
                orderItem.setPackaging(itemRequest.getNewPackaging());
                orderItem.setNumberOfPallets(itemRequest.getNewPalletId());
                orderItem.setItemWeight(itemRequest.getNewWeight());
                orderItem.setPallet(pallet.get());
            }
            // Set the new total price
            o.setTotalWeight(orderUpdateRequestDto.getTotalWeight());
            o.setTotalPrice(orderUpdateRequestDto.getTotalPrice());

            // Update the production date:
            LocalDateTime prod_date = LocalDateTime.now();
            o.setProductionDate(prod_date);
            // Update the new delivery date:
            LocalDateTime newDeliveryDate = LocalDateTime.now().plusHours((long) workingHours);
            o.setDeliveryDate(newDeliveryDate);
        });
        return new ResponseEntity<>("Order has been updated.", HttpStatus.OK);
    }


    @CacheEvict(value = "order", key = "#id")
    @Transactional
    @Override
    public ResponseEntity<Object> updateOrderStatus(Long id, OrderStatusDto orderStatusDto) throws Exception {
        Order order = orderRepository.findById(id).orElse(null);
        if (order == null) {
            return ResponseEntity.notFound().build();

        }

        if(order.getStatus() == OrderStatus.IN_PRODUCTION && orderStatusDto.getNewStatus().equals("CANCELED") ||
        order.getStatus() == OrderStatus.READY_TO_SHIPPED && orderStatusDto.getNewStatus().equals("CANCELED") ||
        order.getStatus() == OrderStatus.SHIPPED && orderStatusDto.getNewStatus().equals("CANCELED")){
            throw new OrderCannotBeCanceledException();
        }

        if(orderStatusDto.getNewStatus().equals("CANCELED")){
            for (OrderItem orderItem : order.getOrderItems()){
                Product product = orderItem.getProduct();
                product.setTotalWeight(product.getTotalWeight() + orderItem.getItemWeight());
            }
        }

        else if (orderStatusDto.getNewStatus().equals("SHIPPED")){
            shipmentService.createShipment(Optional.of(order));
        }


        order.setStatus(OrderStatus.valueOf(orderStatusDto.getNewStatus()));
        orderRepository.save(order);
        return ResponseEntity.ok().build();
    }



    @Transactional
    @Override
    public ResponseEntity<Object> cancelOrder(Long id) {
        Order order = orderRepository.findById(id).orElse(null);

        if (order == null) {
            return ResponseEntity.notFound().build();
        }

        if (order.getStatus() == OrderStatus.IN_PRODUCTION) {
            return new ResponseEntity<>("Order is already in production and cannot be canceled at the moment...", HttpStatus.CONFLICT);
        }
        else if(order.getStatus() == OrderStatus.RECEIVED || order.getStatus() == OrderStatus.CANCELED){
            return new ResponseEntity<>("Order is already received and can not be canceled...", HttpStatus.CONFLICT);
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
