package com.example.medjool.services;


import com.example.medjool.dto.ClientDto;
import com.example.medjool.model.Address;
import com.example.medjool.model.Client;
import com.example.medjool.model.Contact;
import com.example.medjool.repository.AddressRepository;
import com.example.medjool.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClientService {

    private final ClientRepository clientRepository;
    private final AddressRepository addressRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository, AddressRepository addressRepository) {
        this.clientRepository = clientRepository;
        this.addressRepository = addressRepository;
    }

    public List<Client> getAllClients(){
        return clientRepository.findAll();
    }

    public ResponseEntity<Object> addClient(ClientDto clientDto) {

        Client client = new Client();
        Address address = new Address();
        Contact contact = new Contact();
        List<Address> clientAddresses = new ArrayList<>();
        clientDto.getAddressDtoList().stream().forEach(addressDto -> {
            address.setCity(addressDto.getCity());
            address.setCountry(addressDto.getCountry());
            address.setStreet(addressDto.getStreet());
            address.setState(addressDto.getState());
            address.setPostalCode(addressDto.getPostalCode());
            clientAddresses.add(address);
            addressRepository.save(address);
        });

        contact.setEmail(clientDto.getEmail());
        contact.setPhone(clientDto.getPhone());

        client.setName(clientDto.getName());
        client.setAddresses(clientAddresses);
        clientRepository.save(client);

        return new ResponseEntity<>(client, HttpStatus.CREATED);
    }
}
