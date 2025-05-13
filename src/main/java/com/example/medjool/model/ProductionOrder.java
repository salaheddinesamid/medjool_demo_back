package com.example.medjool.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "production_orders")
public class ProductionOrder {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Enumerated(EnumType.STRING)
    private ProductionStatus productionStatus;

    @Column(name = "working_hours")
    private double workingHours;

    @Column(name = "remaining_hours")
    private double remainingHours;

}
