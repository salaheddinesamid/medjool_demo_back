package com.example.medjool.services;

import com.example.medjool.dto.SettingDetailsDto;
import com.example.medjool.dto.SettingUpdateDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface SystemSettingService {
    List<SettingDetailsDto> getSystemSettings();
    ResponseEntity<Object> updateSetting(SettingUpdateDto settingUpdateDto);
}
