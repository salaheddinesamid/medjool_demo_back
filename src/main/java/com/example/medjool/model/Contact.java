package com.example.medjool.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer contactId;

    @Column(name = "department")
    private String department;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;


}
