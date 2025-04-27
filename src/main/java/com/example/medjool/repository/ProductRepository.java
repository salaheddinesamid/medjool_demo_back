package com.example.medjool.repository;

import com.example.medjool.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductRepository extends JpaRepository<Product, String> {
    Product findByCallibreAndColorAndQualityAndFarm(String callibre, String color, String quality,String farm);
}
