package com.example.medjool.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer orderId;

    @ManyToOne
    Client client;

    @Column(name = "packaging")
    Float packaging;



    @Column(name = "status")
    String status;

}
