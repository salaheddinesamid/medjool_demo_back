package com.example.medjool.services;

import com.example.medjool.dto.AddressDto;
import com.example.medjool.dto.ClientDto;
import com.example.medjool.dto.ContactDto;
import com.example.medjool.model.Address;
import com.example.medjool.model.Client;
import com.example.medjool.model.Contact;
import com.example.medjool.repository.AddressRepository;
import com.example.medjool.repository.ClientRepository;
import com.example.medjool.repository.ContactRepository;
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
    private final ContactRepository contactRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository, AddressRepository addressRepository, ContactRepository contactRepository) {
        this.clientRepository = clientRepository;
        this.addressRepository = addressRepository;
        this.contactRepository = contactRepository;
    }



    // Update client information:

    /* To be updated:
    public ResponseEntity<Client> updateClient(Integer clientId,ClientDto updatedClientDto) {

        Client client = clientRepository.findByCompanyName(
                updatedClientDto.getNameOfCompany()
        );


        if(client != null) {
            List<Address> newClientAddresses = new ArrayList<>();
            client.setCompanyName(updatedClientDto.getNameOfCompany());
            for (AddressDto addressDto : updatedClientDto.getAddressDtoList()) {
                Address address = new Address();
                address.setCity(addressDto.getCity());
                address.setCountry(addressDto.getCountry());
                address.setStreet(addressDto.getStreet());
                address.setState(addressDto.getState());
                address.setPostalCode(addressDto.getPostalCode());
                address = addressRepository.save(address);
                newClientAddresses.add(address);
            }
            client.setAddresses(newClientAddresses);

            clientRepository.save(client);

        }
        return new ResponseEntity<>(client, HttpStatus.OK);
    }


     */

    // Delete a client:
    public ResponseEntity<Object> deleteClient(Integer clientId) {
        clientRepository.deleteById(clientId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

