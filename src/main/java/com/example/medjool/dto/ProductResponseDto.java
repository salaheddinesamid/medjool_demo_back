package com.example.medjool.dto;


import com.example.medjool.model.Product;
import lombok.Data;

@Data
public class ProductResponseDto {

    private Long id;

    private String brand;

    private String callibre;

    private Double totalWeight;

    private String color;

    private String farm;

    private String quality;

    private String status;

    public ProductResponseDto(Product product) {
        this.id = product.getProductId();
        this.brand = product.getBrand();
        this.callibre = product.getCallibre();
        this.totalWeight = product.getTotalWeight();
        this.color = product.getColor();
        this.farm = product.getFarm();
        this.quality = product.getQuality();

    }

}
