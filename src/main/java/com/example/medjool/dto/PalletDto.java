package com.example.medjool.dto;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class PalletDto {

    String packaging;
    Integer numberOfBoxesInCarton;
    Integer numberOfCartonsInStory;
    Integer numberOfStoriesInPallet;
    Float x;
    Float y;
    Float totalNet;
    String tag;
}
