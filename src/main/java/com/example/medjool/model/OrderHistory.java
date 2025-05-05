package com.example.medjool.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "order_history")
public class OrderHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    Order order;

    @Column(name = "confirmed_at")
    private LocalDateTime confirmedAt;

    @Column(name = "sent_to_production_at")
    private LocalDateTime sentToProductionAt;

    @Column(name = "in_production_at")
    private LocalDateTime inProductionAt;

    @Column(name = "ready_to_ship_at")
    private LocalDateTime readyToShipAt;

    @Column(name = "shipped_at")
    private LocalDateTime shippedAt;

    @Column(name = "delivered_at")
    private LocalDateTime deliveredAt;

}
