package com.example.medjool.repository;

import com.example.medjool.model.FactorySchedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface FactoryScheduleRepository extends JpaRepository<FactorySchedule,Long> {
    boolean existsByDate(LocalDate date);
}