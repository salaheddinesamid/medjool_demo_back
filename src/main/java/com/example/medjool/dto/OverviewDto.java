package com.example.medjool.dto;

import lombok.Data;

@Data
public class OverviewDto
{

    Double totalStock;
    Long totalOrders;


    double totalOrdersPreProduction;
    double totalOrdersPostProduction;
    Long totalReceivedOrders;

    double totalPreProductionRevenue;
    double totalPostProductionRevenue;
    double totalReceivedOrdersRevenue;
    double totalRevenue;
}
