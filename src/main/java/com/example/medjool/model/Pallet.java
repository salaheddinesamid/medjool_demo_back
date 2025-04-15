package com.example.medjool.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Pallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer palletId;

    @Column(name = "packaging")
    private float packaging;

    @Column(name = "number_of_boxes_in_carton")
    private Integer numberOfBoxesInCarton;

    @Column(name = "number_of_cartons_in_story")
    private Integer numberOfCartonsInStory;

    @Column(name = "number_of_stories_in_pallet")
    private Integer numberOfStoriesInPallet;

    @Column(name = "total_net")
    private Float totalNet;

    @Column(name = "dimensions")
    private String dimensions;

    @Column(name = "tag")
    private String tag;

    @Column(name = "notes")
    private String notes;

    @Column(name = "production_duration")
    private double productionDuration;

    
}
