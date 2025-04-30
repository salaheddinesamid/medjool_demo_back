package com.example.medjool.dto;

import lombok.Data;

@Data
public class NewUserDto {

    private String firstName;
    private String lastName;
    private String email;
    private String roleName;
    private String password;
    private boolean accountLocked;
}
