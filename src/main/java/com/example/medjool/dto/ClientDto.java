package com.example.medjool.dto;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ClientDto {
    private String name;
    private String email;
    private String phone;
    private List<AddressDto> addressDtoList;
}
