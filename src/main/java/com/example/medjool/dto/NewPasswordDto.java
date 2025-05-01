package com.example.medjool.dto;

import lombok.Data;

@Data
public class NewPasswordDto {
    private String oldPassword;
    private String newPassword;
}
