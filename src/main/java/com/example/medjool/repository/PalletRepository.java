package com.example.medjool.repository;

import com.example.medjool.model.Pallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PalletRepository extends JpaRepository<Pallet, Integer> {
}
