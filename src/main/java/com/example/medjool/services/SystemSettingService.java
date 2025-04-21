package com.example.medjool.services;

import org.springframework.http.ResponseEntity;

public interface SystemSettingService {

    ResponseEntity<Object> updateMinProductLevel(double newMinProductLevel);
}
