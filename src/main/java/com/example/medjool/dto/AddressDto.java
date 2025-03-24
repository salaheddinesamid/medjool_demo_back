package com.example.medjool.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class AddressDto {
    private String street;
    private String city;
    private String state;
    private String postalCode;
    private String country;
}
