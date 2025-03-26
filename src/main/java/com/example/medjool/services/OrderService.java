package com.example.medjool.services;


import com.example.medjool.controller.NotificationController;
import com.example.medjool.dto.OrderDto;
import com.example.medjool.dto.OrderStatusDto;
import com.example.medjool.model.Client;
import com.example.medjool.model.Order;
import com.example.medjool.model.Product;
import com.example.medjool.repository.ClientRepository;
import com.example.medjool.repository.OrderRepository;
import com.example.medjool.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ClientRepository clientRepository;
    private final ProductRepository productRepository;
    private final NotificationController notificationController;

    @Autowired
    public OrderService(OrderRepository orderRepository, ClientRepository clientRepository, ProductRepository productRepository, NotificationController notificationController) {
        this.orderRepository = orderRepository;
        this.clientRepository = clientRepository;
        this.productRepository = productRepository;
        this.notificationController = notificationController;
    }


    public ResponseEntity<Object> createOrder(OrderDto orderDto) {
        try {
            LocalDate localDate = LocalDate.now();

            // Fetch the client
            Client client = clientRepository.findByCompanyName(orderDto.getClientName());
            if (client == null) {
                return ResponseEntity.badRequest().body("Client not found.");
            }

            // Fetch the product
            Product product = productRepository.findById(orderDto.getProductId()).orElse(null);
            if (product == null) {
                return ResponseEntity.badRequest().body("Product not found.");
            }

            // Check if the product stock is sufficient
            if (product.getTotalWeight() < orderDto.getTotalWeight()) {
                return ResponseEntity.badRequest().body("Insufficient stock. Available: " + product.getTotalWeight() + " kg.");
            }

            // Deduct stock and save the updated product
            product.setTotalWeight(product.getTotalWeight() - orderDto.getTotalWeight());
            if (product.getTotalWeight() >= 100) {
                notificationController.sendLowStockNotification(
                        "Attention, le produit: " + product.getCallibre() + "-" + product.getQuality() + " est low stock"
                );
            }
            productRepository.save(product);

            // Create and save the order
            Order order = new Order();
            order.setProduct(product);
            order.setOrderDate(localDate);
            order.setClient(client);
            order.setTotalPrice(orderDto.getTotalPrice());
            order.setStatus("PRELIMINARY");
            orderRepository.save(order);

            // Send WebSocket Notification
            String notificationMessage = "Nouvelle commande crée pour " + client.getCompanyName() +
                    " | Produit: " + product.getBrand() + " " + product.getCallibre();
            notificationController.sendLowStockNotification(notificationMessage);

            return ResponseEntity.ok("Order created successfully.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error creating order: " + e.getMessage());
        }
    }

    public ResponseEntity<Object> updateOrderStatus(Long orderId, OrderStatusDto orderStatusDto) {
        Order order = orderRepository.findById(orderId).orElse(null);
        if (order == null) {
            return ResponseEntity.badRequest().body("Order not found.");
        }

        order.setStatus(orderStatusDto.getNewStatus());
        orderRepository.save(order);
        return ResponseEntity.ok("Order updated successfully.");
    }


    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

}
