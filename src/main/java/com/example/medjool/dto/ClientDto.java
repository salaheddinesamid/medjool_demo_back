package com.example.medjool.dto;


import lombok.Data;

import java.util.List;

@Data
public class ClientDto {
    private String companyName;
    private String generalManager;
    private String companyActivity;
    private String website;
    private List<ContactDto> contacts;
    private List<AddressDto> addresses;
    private String status;
}
