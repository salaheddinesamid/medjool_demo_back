package com.example.medjool.dto;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ClientDto {
    private String nameOfCompany;
    private String nameOfGeneralManager;
    private String companyActivity;
    private String email;
    private String phone;
    private List<AddressDto> addressDtoList;
    private String status;
}
