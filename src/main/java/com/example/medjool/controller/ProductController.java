package com.example.medjool.controller;

import com.example.medjool.model.Product;
import com.example.medjool.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/product/")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Integer id){
        return productService.getProduct(id);
    }

    @GetMapping("/")
    public List<Product> getAll(){
        return productService.getAllProducts();
    }

    /*
    @GetMapping("/availability/{id}")
    public ResponseEntity<?> checkProductAvailability(@PathVariable Integer id){
        return productService.productAvailability(id);
    }

     */


}
