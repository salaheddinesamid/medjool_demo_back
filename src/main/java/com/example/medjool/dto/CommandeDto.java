package com.example.medjool.dto;

import lombok.Data;

import java.util.List;

@Data
public class CommandeDto {
    private Integer orderId;
    private String customerName;
    private String status;
    private List<ProductDto> products;
}

