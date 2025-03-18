package com.example.medjool.dto;

import lombok.Data;

@Data
public class ProductDto {

    private Integer productId;
    private String type;
    private String color;
    private Float price;
    private Integer quantity;
}
