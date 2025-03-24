package com.example.medjool.dto;

import lombok.Data;

@Data
public class OrderDto {

    String clientName;
    ShippingDetailsDto shippingDetails;
    Integer productId;
    Float totalWeight;
    Double totalPrice;
    Float packaging;
    Integer numberOfPallets;

}
