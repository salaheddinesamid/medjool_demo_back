package com.example.medjool.dto;

import com.example.medjool.model.Order;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class OrderResponseDto {
    private Long id;
    private String clientName;
    private double totalPrice;
    private double totalWeight;
    private String status;
    private List<OrderItemResponseDto> items;
    private LocalDate productionDate;
    private LocalDate deliveryDate;

    public OrderResponseDto(Order order) {
        this.id = order.getId();
        this.clientName = order.getClient().getCompanyName();
        this.totalPrice = order.getTotalPrice();
        this.totalWeight = order.getTotalWeight();
        this.status = order.getStatus().toString();
        this.items = order.getOrderItems().stream()
                .map(OrderItemResponseDto::new)
                .toList();
        this.productionDate = order.getProductionDate();
        this.deliveryDate = order.getDeliveryDate();
    }
}