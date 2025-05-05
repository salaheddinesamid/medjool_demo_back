package com.example.medjool.dto;

import lombok.Data;

@Data
public class OrderItemUpdateRequestDto {

    private long itemId;

    private String productCode;
    private double newQuantity;
    private double newPrice;
    private double newPackaging;
    private int newNumberOfPallets;
    private double newWeight;
    private Integer newPalletId;

}
