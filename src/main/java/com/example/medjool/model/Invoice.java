package com.example.medjool.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Getter
@Setter
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer invoiceId;

    @ManyToOne
    Customer customer;

    @Column(name = "date")
    LocalDate date;

    @Column(name = "date_of_expiration")
    LocalDate dateOfExpiration;

    @Column(name = "total_amount")
    Float total;


}
