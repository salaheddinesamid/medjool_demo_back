package com.example.medjool.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class OrderRequestDto {
    private String clientName;
    private List<OrderItemRequestDto> items;
    private  ShippingDetailsDto shippingDetails;
}
