package com.example.medjool.services;

import com.example.medjool.dto.*;
import com.example.medjool.model.Client;
import com.example.medjool.model.Pallet;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ConfigurationService {

    ResponseEntity<Object> addClient(ClientDto clientDto);

    ResponseEntity<List<Client>> getAll();

    ResponseEntity<Object> updateClient(Integer clientId, UpdateClientDto updateClientDto);

    ResponseEntity<Object> deleteClient(Integer id) throws ClassNotFoundException;

    ResponseEntity<List<AddressResponseDto>> getClientAddresses(Integer id);

    ResponseEntity<List<AddressResponseDto>> getClientAddressesByClientName(String clientName);

    ResponseEntity<Object> addPallet(PalletDto palletDto);

    ResponseEntity<List<Pallet>> getAllPallets();

    ResponseEntity<Object> updatePallet(Integer id, UpdatePalletDto palletDto);

    ResponseEntity<Object> deletePallet(Integer id);

    ResponseEntity<List<Pallet>> getAllPalletsByPackaging(float packaging);

}
