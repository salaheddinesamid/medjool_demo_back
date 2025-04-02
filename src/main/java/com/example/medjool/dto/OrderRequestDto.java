package com.example.medjool.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderRequestDto {
    private String clientName;

    private List<OrderItemRequestDto> items;
}
