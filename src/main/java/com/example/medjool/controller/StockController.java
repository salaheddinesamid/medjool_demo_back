package com.example.medjool.controller;

import com.example.medjool.dto.OverviewDto;
import com.example.medjool.model.Product;
import com.example.medjool.services.OverviewService;
import com.example.medjool.services.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/stock/")
public class StockController {

    private final StockService stockService;
    private final OverviewService overviewService;

    @Autowired
    public StockController(StockService stockService, OverviewService overviewService) {
        this.stockService = stockService;
        this.overviewService = overviewService;
    }


    @GetMapping("get_all")
    public ResponseEntity<List<Product>> getAll() {
        List<Product> allProducts = stockService.getAllProducts();
        return new ResponseEntity<>(allProducts, HttpStatus.OK);
    }


    @GetMapping("overview")
    public ResponseEntity<OverviewDto> getStockOverview() {
        return overviewService.getOverview();
    }

    
}
