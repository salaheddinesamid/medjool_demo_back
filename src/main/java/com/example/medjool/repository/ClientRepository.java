package com.example.medjool.repository;

import com.example.medjool.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Integer> {
    Client findByCompanyName(String name);
}
