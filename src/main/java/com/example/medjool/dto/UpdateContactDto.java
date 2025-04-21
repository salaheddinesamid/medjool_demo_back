package com.example.medjool.dto;

import lombok.Data;

@Data
public class UpdateContactDto {

    private Integer contactId;
    private String newDepartmentName;
    private String newPhoneNumber;
    private String newEmailAddress;
}
