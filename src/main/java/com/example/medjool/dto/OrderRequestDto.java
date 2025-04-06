package com.example.medjool.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderRequestDto {
    private String clientName;
    private List<OrderItemRequestDto> items;
    private  ShippingDetailsDto shippingDetails;
}
