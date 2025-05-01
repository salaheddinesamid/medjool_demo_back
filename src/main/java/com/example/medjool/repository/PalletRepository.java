package com.example.medjool.repository;

import com.example.medjool.model.Pallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PalletRepository extends JpaRepository<Pallet, Integer> {

    List<Pallet> findAllByPackaging(float packaging);
    Pallet findByPackaging(float packaging);
}
