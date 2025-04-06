package com.example.medjool.dto;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class PalletDto {

    String packaging;
    Integer numberOfBoxesInCarton;
    Integer numberOfCartonsInStory;
    Integer numberOfStoriesInPallet;
    String dimensions;
    Float totalNet;
    String tag;
    String notes;
}
