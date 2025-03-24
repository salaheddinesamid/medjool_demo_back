package com.example.medjool.dto;


import lombok.Data;

import java.util.List;

@Data
public class ClientDto {
    private String nameOfCompany;
    private String nameOfGeneralManager;
    private String companyActivity;
    private String email;
    private String phone;
    private List<AddressDto> addressDtoList;
    private String status;
}
