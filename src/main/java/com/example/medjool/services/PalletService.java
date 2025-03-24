package com.example.medjool.services;

import com.example.medjool.dto.PalletDto;
import com.example.medjool.model.Pallet;
import com.example.medjool.repository.PalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class PalletService {

    private final PalletRepository palletRepository;

    @Autowired
    public PalletService(PalletRepository palletRepository) {
        this.palletRepository = palletRepository;
    }


    public ResponseEntity<Object> addPallet(PalletDto palletDto) {

        Pallet pallet = new Pallet();
        pallet.setNumberOfStoriesInPallet(
                palletDto.getNumberOfStoriesInPallet()
        );
        pallet.setNumberOfBoxesInCarton(
                palletDto.getNumberOfBoxesInCarton()
        );

        pallet.setNumberOfCartonsInStory(
                palletDto.getNumberOfCartonsInStory()
        );

        pallet.setPackaging(palletDto.getPackaging());
        pallet.setTag(palletDto.getTag());
        palletRepository.save(pallet);

        return ResponseEntity.ok().body(pallet);
    }

}
