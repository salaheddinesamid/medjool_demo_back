package com.example.medjool.controller;


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

    // ----- Pallet Configuration: ------------------//

    @PostMapping("/new")
    public ResponseEntity<Object> newPallet(@RequestBody PalletDto palletDto) {
        return configurationService.addPallet(palletDto);
    }

    @PostMapping("/get_all")
    public ResponseEntity<List<Pallet>> getAllPallet() {
        return configurationService.getAllPallets();
    }
}
