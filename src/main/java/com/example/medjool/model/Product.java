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
    Integer productId;

    @Column(name = "brand")
    String brand;

    @Column(name = "callibre")
    String callibre;

    @Column(name = "price_per_kg")
    Float pricePerKg;

    @Column(name = "total_weight")
    Float totalWeight;

    @Column(name = "color")
    String color;

    @Column(name = "farm")
    String farm;

}
