package com.example.medjool.repository;

import com.example.medjool.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    Product findByCallibreAndQuality(String callibre, String quality);
}
