package com.example.medjool.dto;

import lombok.Data;

@Data
public class UserDetailsDto {

    BearerToken bearerToken;
    String firstName;
    String email;
}
