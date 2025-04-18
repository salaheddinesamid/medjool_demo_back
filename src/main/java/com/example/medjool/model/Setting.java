package com.example.medjool.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "settings")
public class Setting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long settingId;

    @Column(name = "attribute_name")
    @Enumerated(EnumType.STRING)
    private SystemSettings attributeName;

    @Column(name = "attribute_value")
    private String attributeValue;

    @Column(name = "description")
    private String description;

    @Column(name = "latest_update")
    private LocalDateTime latestUpdate;

}
