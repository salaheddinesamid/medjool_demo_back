package com.example.medjool.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OrderItemRequestDto {

    private String callibre;

    private String color;

    private String quality;

    private double pricePerKg;

    private double packaging;

    private int numberOfPallets;

    private double itemWeight;

    private Integer palletId;

}