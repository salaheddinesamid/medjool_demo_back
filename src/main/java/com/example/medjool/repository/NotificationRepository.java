package com.example.medjool.repository;

import com.example.medjool.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findAllByRead(boolean read);
    Notification findByContent(String productId);
}
