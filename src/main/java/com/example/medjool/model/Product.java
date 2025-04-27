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
    private String productId;

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

    public void setProductId(String callibre, String quality, String color, String farm) {
        String qualityCode = "" + quality.charAt(0) + quality.charAt(quality.length() - 1);
        this.productId = String.format("%s_%s_%s_%s",
                callibre.charAt(0),
                qualityCode,
                color.charAt(0),
                farm.charAt(0)
        );
    }

}
