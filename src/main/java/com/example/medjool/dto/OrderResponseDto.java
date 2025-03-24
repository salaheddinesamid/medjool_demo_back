package com.example.medjool.dto;

import com.example.medjool.model.Product;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
@Data
public class OrderResponseDto {

    String clientName;
    List<Product> products;
    LocalDate date;
    String status;

}
