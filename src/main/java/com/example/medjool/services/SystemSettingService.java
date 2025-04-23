package com.example.medjool.services;

import org.springframework.http.ResponseEntity;

public interface SystemSettingService {

    ResponseEntity<?> updateMinProductLevel(double newMinProductLevel);
}
