package com.example.medjool.dto;

import lombok.Data;

@Data
public class SettingUpdateDto {

    private String settingName;
    private Object settingValue;
    private String settingDescription;
}
