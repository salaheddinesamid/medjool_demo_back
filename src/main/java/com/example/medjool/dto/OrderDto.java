package com.example.medjool.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderDto {


    String client;
    List<ProductDto> productsDto;
    Float total;

}
