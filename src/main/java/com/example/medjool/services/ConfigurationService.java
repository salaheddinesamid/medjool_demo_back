package com.example.medjool.services;

import com.example.medjool.dto.AddressResponseDto;
import com.example.medjool.dto.ClientDto;
import com.example.medjool.dto.PalletDto;
import com.example.medjool.model.Client;
import com.example.medjool.model.Pallet;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ConfigurationService {

    ResponseEntity<Object> addClient(ClientDto clientDto);
    ResponseEntity<List<Client>> getAll();
    ResponseEntity<List<AddressResponseDto>> getClientAddresses(Integer id);
    ResponseEntity<Object> addPallet(PalletDto palletDto);
    ResponseEntity<List<Pallet>> getAllPallets();
    ResponseEntity<List<Pallet>> getAllPalletsByPackaging(float packaging);

}
