package com.example.medjool.dto;

import lombok.Data;

@Data
public class PalletDto {

    float  packaging;
    Integer numberOfBoxesInCarton;
    Integer numberOfCartonsInStory;
    Integer numberOfStoriesInPallet;
    String dimensions;
    Float totalNet;
    float preparationTime;
    String tag;
    String notes;
}
