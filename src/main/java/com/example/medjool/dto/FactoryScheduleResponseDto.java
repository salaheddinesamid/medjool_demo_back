package com.example.medjool.dto;

import com.example.medjool.model.Order;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class FactoryScheduleResponseDto {
    private LocalDate date;
    private double workingHours;
    private double remainingHours;
    private boolean isAvailable;
    private List<Long> scheduledOrders;
}
