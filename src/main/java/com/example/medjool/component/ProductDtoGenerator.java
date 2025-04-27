package com.example.medjool.component;

import com.example.medjool.dto.NewProductDto;

import java.util.ArrayList;
import java.util.List;

public class ProductDtoGenerator {

    private static final String[] CALIBRES = { "BBS", "Small", "Medium", "Large", "Jumbo", "Super Jumbo" };
    private static final String[] QUALITIES = { "Export A", "Export B", "Export C", "GMS", "ML" };
    private static final String[] FARMS = { "Medjool", "Domaines" };
    private static final String DEFAULT_COLOR = "Dark"; // or Light
    private static final double DEFAULT_WEIGHT = 0.0;   // initialize stock as 0

    public static List<NewProductDto> generateAllProductDtos() {
        List<NewProductDto> productDtos = new ArrayList<>();

        for (String calibre : CALIBRES) {
            for (String quality : QUALITIES) {
                for (String farm : FARMS) {
                    productDtos.add(new NewProductDto(
                            calibre,
                            quality,
                            DEFAULT_COLOR,
                            farm,
                            DEFAULT_WEIGHT
                    ));
                }
            }
        }

        return productDtos;
    }
}
