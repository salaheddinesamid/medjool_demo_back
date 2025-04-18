package com.example.medjool.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class UserDetailsDto {
    private long userId;
    private String firstName;
    private String lastName;
    private String email;
    private String role;
    private boolean active;
}
