package com.example.medjool.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false) // Ensures foreign key constraint
    private Client client;

    @ManyToOne
    Product product;

    private LocalDate orderDate;


    @Column(name = "number_of_pallets")
    Integer numberOfPallets;

    @Column(name = "packaging")
    Float packaging;

    @Column(name = "total_weight")
    private Long totalWeight;

    @Column(name = "total_price", nullable = false)
    private Double totalPrice;

    @Column(name = "status", nullable = false)
    private String status;

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", client=" + (client != null ? client.getCompanyName() : "N/A") +
                ", orderDate='" + orderDate + '\'' +
                ", totalPrice=" + totalPrice +
                ", status='" + status + '\'' +
                '}';
    }
}
