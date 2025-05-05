package com.example.medjool.repository;

import com.example.medjool.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findByCallibreAndColorAndQualityAndFarm(String callibre, String color, String quality,String farm);
    Optional<Product> findByProductCode(String productCode);
    boolean existsByProductCode(String productCode);
}
