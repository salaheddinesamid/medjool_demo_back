package com.example.medjool.dto;

import lombok.Data;

@Data
public class BearerTokenDto {

    private String token;

    public BearerTokenDto(String token) {
        this.token = token;
    }
}
