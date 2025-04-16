package com.example.medjool.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestDto {
    private String clientName;
    private List<OrderItemRequestDto> items;
    private LocalDate productionDate;
    private  ShippingDetailsDto shippingDetails;
}
