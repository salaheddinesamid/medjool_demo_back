package com.example.medjool.repository;

import com.example.medjool.model.SystemSetting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SystemSettingRepository extends JpaRepository<SystemSetting, Long> {
    SystemSetting findByKey(String key);
}
