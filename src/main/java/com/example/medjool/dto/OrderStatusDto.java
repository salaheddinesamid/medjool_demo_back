package com.example.medjool.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderStatusDto {
    String newStatus;
    LocalDateTime preferredProductionDate;
}
