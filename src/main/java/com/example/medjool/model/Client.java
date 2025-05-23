package com.example.medjool.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer clientId;

    @Column(name = "company_name")
    String companyName;

    @Column(name = "general_manager")
    String generalManager;

    @Column(name = "company_activity")
    String companyActivity;

    @Column(name = "SIRET")
    private String SIRET;

    @Column(name = "web_site")
    private String webSite;

    @Column(name = "preferred_product_quality")
    private String preferredProductQuality;

    @OneToMany
    List<Address> addresses;

    @OneToMany
    List<Contact> contacts;

   @Enumerated(EnumType.STRING)
   ClientStatus clientStatus;
}
