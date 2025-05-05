package com.example.medjool.dto;

import lombok.Data;

@Data
public class OrderHistoryDto {

    private Long orderId;
    private String newStatus;

}
