package com.example.medjool.dto;

import lombok.Data;

import java.util.List;

@Data
public class UpdateClientDto {

    private String newCompanyName;
    private String newGeneralManager;
    private String newCompanyActivity;
    private List<UpdateAddressDto> newAddresses;
    private List<UpdateContactDto> newContacts;
}
