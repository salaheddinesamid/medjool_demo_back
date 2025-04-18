package com.example.medjool.services;


import com.example.medjool.dto.NotificationResponseDto;

import java.util.List;

public interface AlertService {

    void newAlert(String content);
    List<NotificationResponseDto> getAllAlerts();
    void markAllAsRead();
    void markAsRead(Long id);
    boolean isExists(String content);
}
