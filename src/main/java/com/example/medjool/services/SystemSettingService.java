package com.example.medjool.services;

import com.example.medjool.dto.SettingDetailsDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface SystemSettingService {

    ResponseEntity<?> updateMinProductLevel(double newMinProductLevel);
    ResponseEntity<List<SettingDetailsDto>> getAllSettings();
}
