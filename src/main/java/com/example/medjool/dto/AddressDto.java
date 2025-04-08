package com.example.medjool.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AddressDto {


    private String street;
    private String city;
    private String state;
    private String postalCode;
    private String country;


}
