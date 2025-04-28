package com.example.medjool.dto;

import lombok.Data;

@Data
public class ShipmentDetailsDto {
    private long shipmentId;
    private String trackingNumber;
    private String trackingUrl;
    private OrderResponseDto orderDetails;

}
