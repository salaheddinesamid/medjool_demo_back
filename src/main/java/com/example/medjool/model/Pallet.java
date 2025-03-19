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
    Integer palletId;

    @Column(name = "packaging")
    String packaging;

    @Column(name = "number_of_boxes_in_carton")
    Integer numberOfBoxesInCarton;

    @Column(name = "number_of_cartons_in_story")
    Integer numberOfCartonsInStory;

    @Column(name = "number_of_stories_in_pallet")
    Integer numberOfStoriesInPallet;


    @Column(name = "tag")
    String tag;
}
