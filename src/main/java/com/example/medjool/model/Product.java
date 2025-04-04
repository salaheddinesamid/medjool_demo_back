package com.example.medjool.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private String callibre;


    @Column(name = "total_weight", nullable = false)
    private Double totalWeight;

    @Column(nullable = false)
    private String color;

    @Column(nullable = false)
    private String farm;

    @Column(nullable = false)
    private String quality;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProductStatus status;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<OrderItem> orderItems;

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
