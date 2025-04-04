package com.example.medjool.dto;

import lombok.Data;

@Data
public class OverviewDto
{

    Double totalStock;
    Long totalOrders;
    Long totalReceivedOrders;
    Float totalRevenue;
}
