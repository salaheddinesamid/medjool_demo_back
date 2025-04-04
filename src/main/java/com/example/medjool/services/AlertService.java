package com.example.medjool.services;

import com.example.medjool.dto.NotificationResponseDto;
import com.example.medjool.model.Notification;
import com.example.medjool.repository.NotificationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AlertService {

    private final NotificationRepository notificationRepository;

    public AlertService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public void newAlert(String content) {
        Notification notification = new Notification();
        LocalDateTime now = LocalDateTime.now();
        notification.setDate(now);
        notification.setContent(content);
        notification.setRead(false);
        notificationRepository.save(notification);
    }


    public List<NotificationResponseDto> getAllAlerts() {
        List<Notification> alerts = notificationRepository.findAll();
        return alerts.stream().map(NotificationResponseDto::new).toList();
    }

    public void markAllAsRead() {
        for(Notification notification : notificationRepository.findAll()) {
            notification.setRead(true);
            notificationRepository.save(notification);
            notificationRepository.delete(notification);
        }
    }

    public void markAsRead(Long id) {
        notificationRepository.deleteById(id);
    }
}
