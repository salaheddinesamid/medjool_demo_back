package com.example.medjool.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter

@AllArgsConstructor
@NoArgsConstructor
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

    @Column(name = "number_of_boxes_in_story")
    private Integer numberOfBoxesInStory;


    @Column(name = "number_of_boxes_in_pallet")
    private Integer numberOfBoxesInPallet;


    @Column(name = "height")
    private float height;

    @Column(name = "width")
    private float width;

    @Column(name = "length")
    private float length;

    @Column(name = "total_net")
    private Float totalNet;


    @Column(name = "tag")
    private String tag;

    @Column(name = "notes")
    private String notes;

    @Column(name = "preparation_time_in_hours")
    private double preparationTime;


}
