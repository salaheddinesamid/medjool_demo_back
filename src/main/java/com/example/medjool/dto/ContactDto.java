package com.example.medjool.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ContactDto {

    private String department;
    private String email;
    private String phone;
}
