package com.example.medjool.component;

import com.example.medjool.dto.NewProductDto;
import com.example.medjool.services.implementation.StockServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StockInitializer {

    @Autowired
    private StockServiceImpl stockService;

    @EventListener(ApplicationReadyEvent.class)
    public void initialize() {

        List<NewProductDto> allProducts = ProductDtoGenerator.generateAllProductDtos();
        for (NewProductDto dto : allProducts) {
            stockService.createNewProduct(dto);
        }
    }
}
