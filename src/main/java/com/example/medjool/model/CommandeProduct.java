package com.example.medjool.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class CommandeProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;

    @ManyToOne
    @JoinColumn(name = "commande_id", nullable = false)
    Commande commande;

    @ManyToOne
    @JoinColumn(name = "product_id",nullable = false)
    Product product;

    @Column(name = "quantity")
    Integer quantity;
}
