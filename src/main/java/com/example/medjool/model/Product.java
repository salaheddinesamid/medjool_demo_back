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

    @Column(name = "type")
    String type;

    @Column(name = "color")
    String color;

    @Column(name = "capacity")
    Float packaging;

    @Column(name = "price")
    Float price;

    @Column(name = "quantity")
    Integer quantity;



}
