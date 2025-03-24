package com.example.medjool.controller;

import com.example.medjool.dto.PalletDto;
import com.example.medjool.model.Pallet;
import com.example.medjool.services.PalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pallet")
public class PalletController {
    private final PalletService palletService;

    @Autowired
    public PalletController(PalletService palletService) {
        this.palletService = palletService;
    }

    @PostMapping("/new")
    public ResponseEntity<Object> newPallet(@RequestBody PalletDto palletDto) {
        return palletService.addPallet(palletDto);
    }



}
