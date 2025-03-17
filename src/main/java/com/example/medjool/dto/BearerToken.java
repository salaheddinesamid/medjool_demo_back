package com.example.medjool.dto;

import lombok.Data;

@Data
public class BearerToken {
    String token;

    public BearerToken(String token){
        this.token = token;
    }
}
