package com.example.medjool.controller;

import com.example.medjool.dto.SettingDetailsDto;
import com.example.medjool.dto.SettingUpdateDto;
import com.example.medjool.services.implementation.SystemSettingServiceImpl;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/system-settings")
@NoArgsConstructor
public class SystemSettingsController {

    private SystemSettingServiceImpl systemSettingsService;

    // Get all system settings
    @GetMapping("get_all")
    public List<SettingDetailsDto> getAllSettings(){
        return systemSettingsService.getSystemSettings();
    }

    /* To be implemented
    @PutMapping("/update/")
    public ResponseEntity<Object> updateSetting(@RequestParam String settingName, @RequestBody SettingUpdateDto settingUpdateDto){
        return
    }

     */
}
