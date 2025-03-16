package com.example.medjool.repository;

import com.example.medjool.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer,Integer> {

    Customer findByName(String name);
}
