package com.example.medjool.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderHistoryResponseDto {

    private long historyId;
    private Long orderNumber;
    private String clientName;
    private LocalDateTime confirmedAt;
    private LocalDateTime sentToProductionAt;
    private LocalDateTime inProductionAt;
    private LocalDateTime readyToShipAt;
    private LocalDateTime shippedAt;
    private LocalDateTime deliveredAt;
    private LocalDateTime receivedAt;


}
