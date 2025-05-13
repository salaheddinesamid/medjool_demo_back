package com.example.medjool.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
public class FactorySchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scheduleId;

    @Column(name = "date_time")
    private LocalDateTime dateTime;

    @Column(name = "working_hours")
    private Integer workingHours;

    @Column(name = "remaining_hours")
    private Integer remainingHours;

    @Column(name = "is_available")
    private Boolean isAvailable;

    @OneToMany
    List<Order> orders;
}
