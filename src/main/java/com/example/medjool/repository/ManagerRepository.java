package com.example.medjool.repository;

import com.example.medjool.model.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManagerRepository extends JpaRepository<Manager,Integer> {

    Manager findByEmail(String email);
}
