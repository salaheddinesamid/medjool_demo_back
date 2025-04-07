package com.example.medjool.controller;


import com.example.medjool.dto.NotificationResponseDto;
import com.example.medjool.services.implementation.AlertServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/alert")
public class AlertController {
    private final AlertServiceImpl alertService;

    public AlertController(AlertServiceImpl alertService) {
        this.alertService = alertService;
    }


    @GetMapping("")
    public List<NotificationResponseDto> getAlerts(){
        return alertService.getAllAlerts();
    }


    @PutMapping("/read_all")
    public void readAll(){
        alertService.markAllAsRead();
    }
}
