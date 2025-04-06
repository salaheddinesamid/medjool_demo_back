package com.example.medjool.dto;

import jakarta.persistence.Entity;
import lombok.Data;

@Data
public class ShippingDetailsDto {

    String transportType;
    String incoterm;
    Long addressId;
}
