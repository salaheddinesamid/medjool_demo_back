package com.example.medjool.dto;

import lombok.Data;

@Data
public class ShipmentDetailsDto {
    private long shipmentId;
    private long orderId;
    private String trackingNumber;
}
