package com.example.medjool.services.implementation;

import com.example.medjool.dto.SettingDetailsDto;
import com.example.medjool.dto.SettingUpdateDto;
import com.example.medjool.services.SystemSettingService;
import org.springframework.http.ResponseEntity;

import java.util.List;



public class SystemSettingServiceImpl implements SystemSettingService {
    @Override
    public List<SettingDetailsDto> getSystemSettings() {
        return List.of();
    }

    @Override
    public ResponseEntity<Object> updateSetting(SettingUpdateDto settingUpdateDto) {
        return null;
    }
}
