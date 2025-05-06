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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{


    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderHistoryRepository orderHistoryRepository;
    private final ClientRepository clientRepository;
    private final PalletRepository palletRepository;
    private final ShipmentServiceImpl shipmentService;
    private final OrderItemRepository orderItemRepository;

    Logger logger = Logger.getLogger(OrderService.class.getName());

    @Override
    @Transactional
    public ResponseEntity<?> createOrder(OrderRequestDto orderRequest) {
        logger.info("New Order is being processed...");

        // Validate client
        Client client = Optional.ofNullable(clientRepository.findByCompanyName(orderRequest.getClientName()))
                .filter(c -> c.getClientStatus() == ClientStatus.ACTIVE)
                .orElseThrow(ClientNotActiveException::new);

        Order order = new Order();
        orderRepository.save(order);


        List<OrderItem> orderItems = new ArrayList<>();

        double totalPrice = 0.0;
        double totalWeight = 0.0;
        long estimatedDeliveryTime = 0;

        for (OrderItemRequestDto itemDto : orderRequest.getItems()) {
            Product product = productRepository.findByProductCode(itemDto.getProductCode())
                    .orElseThrow(ProductNotFoundException::new);

            if (product.getTotalWeight() < itemDto.getItemWeight()) {
                throw new ProductLowStock();
            }

            // Deduct product weight
            product.setTotalWeight(product.getTotalWeight() - itemDto.getItemWeight());

            Pallet pallet = palletRepository.findById(itemDto.getPalletId())
                    .orElseThrow(() -> new RuntimeException("Pallet not found"));

            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setItemWeight(itemDto.getItemWeight());
            orderItem.setPricePerKg(itemDto.getPricePerKg());
            orderItem.setPackaging(itemDto.getPackaging());
            orderItem.setNumberOfPallets(itemDto.getNumberOfPallets());
            orderItem.setOrderCurrency(OrderCurrency.valueOf(orderRequest.getCurrency()));
            orderItem.setPallet(pallet);
            orderItem.setOrder(order);

            orderItems.add(orderItem);

            totalPrice += itemDto.getPricePerKg() * itemDto.getItemWeight();
            totalWeight += itemDto.getItemWeight();
            estimatedDeliveryTime += pallet.getPreparationTime();
        }

        // Save updated products and order items
        productRepository.saveAll(orderItems.stream().map(OrderItem::getProduct).collect(Collectors.toList()));
        orderItemRepository.saveAll(orderItems);

        // Set order fields
        order.setClient(client);
        order.setOrderItems(orderItems);
        order.setTotalPrice(totalPrice);
        order.setTotalWeight(totalWeight);
        order.setProductionDate(LocalDateTime.now());
        order.setStatus(OrderStatus.PRELIMINARY);
        order.setCurrency(OrderCurrency.valueOf(orderRequest.getCurrency()));
        order.setShippingAddress(orderRequest.getShippingAddress());
        order.setDeliveryDate(LocalDateTime.now().plusHours(estimatedDeliveryTime));

        return ResponseEntity.ok("Order has been created successfully.");
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
                throw new OrderCannotBeCanceledException("Order cannot be updated at this stage.");
            }

            // Calculate the new delivery date:
            double workingHours = 0;

            for (OrderItemUpdateRequestDto itemRequest : orderUpdateRequestDto.getUpdatedItems()) {
                Product product = productRepository.findByProductCode(itemRequest.getProductCode()).get();
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

        OrderHistoryDto orderHistoryDto = new OrderHistoryDto();
        orderHistoryDto.setOrderId(id);
        orderHistoryDto.setNewStatus(orderStatusDto.getNewStatus());

        if(order.getStatus() == OrderStatus.IN_PRODUCTION && orderStatusDto.getNewStatus().equals("CANCELED") ||
        order.getStatus() == OrderStatus.READY_TO_SHIPPED && orderStatusDto.getNewStatus().equals("CANCELED") ||
        order.getStatus() == OrderStatus.SHIPPED && orderStatusDto.getNewStatus().equals("CANCELED")){
            throw new OrderCannotBeCanceledException("Order cannot be canceled at this stage.");
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
        addOrderHistory(orderHistoryDto);
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

    @Override
    public void addOrderHistory(OrderHistoryDto orderHistoryDto) {
        Order order = orderRepository.findById(orderHistoryDto.getOrderId()).orElse(null);
        if (order == null) {
            throw new IllegalArgumentException("Order not found for ID: " + orderHistoryDto.getOrderId());
        }

        OrderHistory orderHistory = orderHistoryRepository.findByOrderId(orderHistoryDto.getOrderId());
        if (orderHistory == null) {
            orderHistory = new OrderHistory();
            orderHistory.setOrder(order);
        }

        switch (orderHistoryDto.getNewStatus()) {
            case "CONFIRMED" -> {
                if (orderHistory.getConfirmedAt() == null) {
                    orderHistory.setConfirmedAt(LocalDateTime.now());
                }
            }
            case "SENT_TO_PRODUCTION" -> {
                if (orderHistory.getSentToProductionAt() == null) {
                    orderHistory.setSentToProductionAt(LocalDateTime.now());
                }
            }
            case "IN_PRODUCTION" -> {
                if (orderHistory.getInProductionAt() == null) {
                    orderHistory.setInProductionAt(LocalDateTime.now());
                }
            }
            case "READY_TO_SHIPPED" -> {
                if (orderHistory.getReadyToShipAt() == null) {
                    orderHistory.setReadyToShipAt(LocalDateTime.now());
                }
            }
            case "SHIPPED" -> {
                if (orderHistory.getShippedAt() == null) {
                    orderHistory.setShippedAt(LocalDateTime.now());
                }
            }
            case "RECEIVED" -> {
                if (orderHistory.getDeliveredAt() == null) {
                    orderHistory.setDeliveredAt(LocalDateTime.now());
                }
            }
            default -> throw new IllegalArgumentException("Invalid status: " + orderHistoryDto.getNewStatus());
        }

        orderHistoryRepository.save(orderHistory);
        logger.info("Order history has been updated successfully.");
    }

    @Override
    public ResponseEntity<List<OrderHistoryResponseDto>> getAllOrderHistory() {

        List<OrderHistory> orderHistories = orderHistoryRepository.findAll();

        List<OrderHistoryResponseDto> response = orderHistories.stream()
                .map(orderHistory ->{
                    OrderHistoryResponseDto orderHistoryResponseDto = new OrderHistoryResponseDto();
                    orderHistoryResponseDto.setOrderNumber(orderHistory.getOrder().getId());
                    orderHistoryResponseDto.setClientName(orderHistory.getOrder().getClient().getCompanyName());
                    orderHistoryResponseDto.setConfirmedAt(orderHistory.getConfirmedAt());
                    orderHistoryResponseDto.setSentToProductionAt(orderHistory.getSentToProductionAt());
                    orderHistoryResponseDto.setInProductionAt(orderHistory.getInProductionAt());
                    orderHistoryResponseDto.setReadyToShipAt(orderHistory.getReadyToShipAt());
                    orderHistoryResponseDto.setShippedAt(orderHistory.getShippedAt());
                    orderHistoryResponseDto.setDeliveredAt(orderHistory.getDeliveredAt());
                    return orderHistoryResponseDto;
                })
                .toList();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
