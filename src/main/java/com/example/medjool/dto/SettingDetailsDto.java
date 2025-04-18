package com.example.medjool.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SettingDetailsDto {

    private Long id;
    private String attributeName;
    private String attributeValue;
    private String attributeDescription;
    private LocalDateTime latestUpdate;
}
