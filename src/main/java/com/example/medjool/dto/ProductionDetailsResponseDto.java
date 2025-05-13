package com.example.medjool.dto;

import com.example.medjool.model.ProductionOrder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProductionDetailsResponseDto {

    private Long productionId;
    private Long orderNumber;
    private LocalDateTime startDate;
    private double workingHours;
    private double remainingHours;
    private String status;

    public ProductionDetailsResponseDto(ProductionOrder productionOrder) {
        this.productionId = productionOrder.getId();
        this.orderNumber = productionOrder.getOrder().getId();
        this.startDate = productionOrder.getStartDate();
        this.workingHours = productionOrder.getWorkingHours();
        this.remainingHours = productionOrder.getRemainingHours();
        this.status = productionOrder.getProductionStatus().toString();
    }
}
