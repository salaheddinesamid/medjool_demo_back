package com.example.medjool.services;


import com.example.medjool.dto.OverviewDto;
import org.springframework.http.ResponseEntity;

public interface OverviewService {
    ResponseEntity<OverviewDto> getOverview();
}
