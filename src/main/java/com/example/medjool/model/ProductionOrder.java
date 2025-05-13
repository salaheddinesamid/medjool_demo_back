package com.example.medjool.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class ProductionOrder {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @Column(name = "order_id")
    private Order order;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "working_hours")
    private Integer workingHours;

}
