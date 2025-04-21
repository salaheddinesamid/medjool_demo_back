package com.example.medjool.dto;

import lombok.Data;

@Data
public class AuthenticationResponseDto {
    String token;
    UserDetailsDto user;
}
