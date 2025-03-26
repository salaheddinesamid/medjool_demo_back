package com.example.medjool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class NotificationController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public void sendLowStockNotification(String productDetails) {
        messagingTemplate.convertAndSend("/topic/track", productDetails);
    }
}