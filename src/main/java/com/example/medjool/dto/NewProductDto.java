package com.example.medjool.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewProductDto {
    private String callibre;
    private String quality;
    private String color;
    private String farm;
    private double totalWeight;
}
