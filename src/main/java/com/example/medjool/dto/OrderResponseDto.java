package com.example.medjool.dto;

import com.example.medjool.model.Order;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class OrderResponseDto {
    private Long id;
    private String clientName;
    private double totalPrice;
    private String currency;
    private double totalWeight;
    private String status;

    private List<OrderItemResponseDto> items;
    private LocalDateTime productionDate;
    private LocalDateTime deliveryDate;
    private double workingHours;
    private String shippingAddress;

    public OrderResponseDto(Order order) {
        this.id = order.getId();
        this.clientName = order.getClient().getCompanyName();
        this.totalPrice = order.getTotalPrice();
        this.currency = order.getCurrency().toString();
        this.totalWeight = order.getTotalWeight();
        this.status = order.getStatus().toString();
        this.items = order.getOrderItems().stream()
                .map(OrderItemResponseDto::new)
                .toList();
        this.productionDate = order.getProductionDate();
        this.deliveryDate = order.getDeliveryDate();
        this.workingHours = order.getWorkingHours();
        this.shippingAddress  = order.getShippingAddress();
    }
}