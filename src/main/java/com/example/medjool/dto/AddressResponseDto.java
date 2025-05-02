package com.example.medjool.dto;

import lombok.Data;

@Data
public class AddressResponseDto {

    private String addressId;
    private String country;
    private String city;
    private String state;
    private String postalCode;
    private String street;
}
