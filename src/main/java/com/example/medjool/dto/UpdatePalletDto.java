package com.example.medjool.dto;

import lombok.Data;

@Data
public class UpdatePalletDto {

    Long palletId;
    float  packaging;
    Integer numberOfBoxesInCarton;
    Integer numberOfCartonsInStory;
    Integer numberOfStoriesInPallet;

    private Integer numberOfBoxesInStory;
    private Integer numberOfBoxesInPallet;

    float height;
    float width;
    float length;

    Float totalNet;
    float preparationTime;
    String tag;
    String notes;

}
