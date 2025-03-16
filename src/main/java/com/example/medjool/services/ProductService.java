package com.example.medjool.services;

import com.example.medjool.model.Product;
import com.example.medjool.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product getProduct(Integer productId){
        return productRepository.findById(productId).get();
    }


    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }
}
