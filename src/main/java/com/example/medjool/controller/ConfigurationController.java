package com.example.medjool.controller;


import com.example.medjool.dto.AddressResponseDto;
import com.example.medjool.dto.ClientDto;
import com.example.medjool.dto.PalletDto;
import com.example.medjool.model.Client;
import com.example.medjool.model.Pallet;
import com.example.medjool.services.ConfigurationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/configuration")
public class ConfigurationController {

    private final ConfigurationService configurationService;

    public ConfigurationController(ConfigurationService configurationService) {
        this.configurationService = configurationService;
    }

    // ----- Client Configuration: ------------------//

    @PostMapping("/client/new")
    public ResponseEntity<Object> addNewClient(@RequestBody ClientDto client) {
        return configurationService.addClient(client);
    }

    @GetMapping("client/get_all")
    public ResponseEntity<List<Client>> getAllClients() {
        return configurationService.getAll();
    }

    @GetMapping("client/addresses/{clientId}")
    public ResponseEntity<List<AddressResponseDto>> getClientAddresses(@PathVariable Integer clientId) {
        return configurationService.getClientAddresses(clientId);
    }

    // ----- Pallet Configuration: ------------------//

    @PostMapping("pallet/new")
    public ResponseEntity<Object> newPallet(@RequestBody PalletDto palletDto) {
        return configurationService.addPallet(palletDto);
    }

    @GetMapping("pallet/get_all")
    public ResponseEntity<List<Pallet>> getAllPallet() {
        return configurationService.getAllPallets();
    }

    @GetMapping("pallet/get_by_packaging/{packaging}")
    public ResponseEntity<List<Pallet>> getPalletByPackaging(
            @PathVariable String packaging
    ) {
        return configurationService.getAllPalletsByPackaging(packaging);
    }



}
