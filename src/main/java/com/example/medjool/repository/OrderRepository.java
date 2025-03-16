package com.example.medjool.repository;

import com.example.medjool.model.Commande;
import com.example.medjool.model.Commande;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Commande,Integer> {
}
