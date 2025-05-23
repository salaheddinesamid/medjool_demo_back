package com.example.medjool.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Shipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long shipmentId;

    private String trackingNumber;

    @Column(name = "tracking_url")
    private String trackingUrl;

    @OneToOne
    private Order order;

}
