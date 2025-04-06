package com.example.medjool.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemRequestDto {

    private String callibre;

    private String color;

    private String quality;

    private double pricePerKg;

    private double packaging;

    private int numberOfPallets;

    private double itemWeight;

    private Long palletId;

}