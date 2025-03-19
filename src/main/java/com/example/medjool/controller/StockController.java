package com.example.medjool.controller;

import com.example.medjool.model.Product;
import com.example.medjool.services.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/stock/")
public class StockController {

    private final StockService stockService;

    @Autowired
    public StockController(StockService stockService) {
        this.stockService = stockService;
    }


    @GetMapping("get_all")
    public ResponseEntity<List<Product>> getAll() {
        List<Product> allProducts = stockService.getAllProducts();
        return new ResponseEntity<>(allProducts, HttpStatus.OK);
    }

    /*
    @GetMapping("/availability/{id}")
    public ResponseEntity<?> checkProductAvailability(@PathVariable Integer id){
        return productService.productAvailability(id);
    }

     */


}
