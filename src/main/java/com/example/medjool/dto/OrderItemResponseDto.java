package com.example.medjool.dto;

import com.example.medjool.model.OrderItem;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemResponseDto {
    private Long id;
    private Long productId;
    private String productName;
    private String callibre;
    private double pricePerKg;
    private double packaging;
    private int numberOfPallets;
    private double itemWeight;

    public OrderItemResponseDto(OrderItem orderItem) {
        this.id = orderItem.getId();
        this.productId = orderItem.getProduct().getProductId();
        this.productName = orderItem.getProduct().getCallibre();
        this.pricePerKg = orderItem.getPricePerKg();
        this.packaging = orderItem.getPackaging();
        this.callibre = orderItem.getProduct().getCallibre();
        this.numberOfPallets = orderItem.getNumberOfPallets();
        this.itemWeight = orderItem.getItemWeight();
    }
}