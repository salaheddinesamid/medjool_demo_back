package com.example.medjool.repository;

import com.example.medjool.model.CommandeProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommandeProductRepository extends JpaRepository<CommandeProduct,Integer> {
}
