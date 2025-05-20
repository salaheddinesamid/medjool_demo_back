package com.example.medjool.controller;

import com.example.medjool.dto.*;
import com.example.medjool.model.Client;
import com.example.medjool.model.Pallet;
import com.example.medjool.services.implementation.ConfigurationServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/configuration")
public class ConfigurationController {

    private final ConfigurationServiceImpl configurationService;

    public ConfigurationController(ConfigurationServiceImpl configurationService) {
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

    @DeleteMapping("client/delete/{clientId}")
    public ResponseEntity<Object> deleteClient(@PathVariable Integer clientId) throws ClassNotFoundException {
        return configurationService.deleteClient(clientId);
    }

    @PutMapping("client/update/{clientId}")
    public ResponseEntity<Object> updateClient(@PathVariable Integer clientId, @RequestBody UpdateClientDto updateClientDto) {
        return configurationService.updateClient(clientId, updateClientDto);
    }

    /*
    @GetMapping("client/addresses/{clientId}")
    public ResponseEntity<List<AddressResponseDto>> getClientAddresses(@PathVariable Integer clientId) {
        return configurationService.getClientAddresses(clientId);
    }

     */

    @GetMapping("client/addresses/{clientName}")
    public ResponseEntity<List<AddressResponseDto>> getClientAddressesByName(@PathVariable String clientName) {
        return configurationService.getClientAddressesByClientName(clientName);
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
            @PathVariable float packaging
    ) {
        return configurationService.getAllPalletsByPackaging(packaging);
    }

    @DeleteMapping("pallet/delete/{palletId}")
    public ResponseEntity<Object> deletePallet(@PathVariable Integer palletId) {
        return configurationService.deletePallet(palletId);
    }

    @PutMapping("pallet/update/{palletId}")
    public ResponseEntity<Object> updatePallet(@PathVariable Integer palletId, @RequestBody UpdatePalletDto palletDto) {
        return configurationService.updatePallet(palletId, palletDto);
    }

    @GetMapping("pallet/get_by_id/{palletId}")
    public Pallet getById(@PathVariable Integer palletId) {
        return configurationService.getPalletById(palletId);
    }



}
