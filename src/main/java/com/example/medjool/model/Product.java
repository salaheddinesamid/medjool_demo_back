package com.example.medjool.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productId;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private String callibre;

    @Column(name = "price_per_kg", nullable = false)
    private Float pricePerKg;

    @Column(name = "total_weight", nullable = false)
    private Float totalWeight;

    @Column(nullable = false)
    private String color;

    @Column(nullable = false)
    private String farm;

    @Column(nullable = false)
    private String quality;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProductStatus status;

    @PrePersist
    @PreUpdate
    public void updateStatus() {
        if (totalWeight != null && totalWeight > 100) {
            this.status = ProductStatus.AVAILABLE;
        } else {
            this.status = ProductStatus.NOT_AVAILABLE;
        }
    }
}
