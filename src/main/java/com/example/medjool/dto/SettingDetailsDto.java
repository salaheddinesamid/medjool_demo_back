package com.example.medjool.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
public class SettingDetailsDto {
    private long id;
    private String key;
    private double  value;

}
