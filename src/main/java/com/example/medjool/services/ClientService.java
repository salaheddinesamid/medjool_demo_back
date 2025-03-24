package com.example.medjool.services;

import com.example.medjool.dto.AddressDto;
import com.example.medjool.dto.ClientDto;
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

    public List<Client> getAllClients(){
        return clientRepository.findAll();
    }

    public ResponseEntity<Object> addClient(ClientDto clientDto) {
        Client client = new Client();
        List<Address> clientAddresses = new ArrayList<>();
        List<Contact> clientContacts = new ArrayList<>();

        // Create and save Addresses properly
        clientDto.getAddressDtoList().forEach(addressDto -> {
            Address address = new Address(); // ✅ Create a NEW instance for each address
            address.setCity(addressDto.getCity());
            address.setCountry(addressDto.getCountry());
            address.setStreet(addressDto.getStreet());
            address.setState(addressDto.getState());
            address.setPostalCode(addressDto.getPostalCode());

            // Save the address
            address = addressRepository.save(address);
            clientAddresses.add(address);
        });

        // Create and save Contact
        Contact contact = new Contact();
        contact.setEmail(clientDto.getEmail());
        contact.setPhone(clientDto.getPhone());

        contact = contactRepository.save(contact);
        clientContacts.add(contact);

        // Set Client details
        client.setCompanyName(clientDto.getNameOfCompany());
        client.setAddresses(clientAddresses);
        client.setContacts(clientContacts);
        client.setCompanyActivity(clientDto.getCompanyActivity());
        client.setGeneralManager(clientDto.getNameOfGeneralManager());

        // Save the client
        clientRepository.save(client);

        return new ResponseEntity<>(client, HttpStatus.CREATED);
    }

    public ResponseEntity<List<Client>> getAll(){
        List<Client> clients = clientRepository.findAll();
        return new ResponseEntity<>(clients,HttpStatus.OK);
    }


    /*
    public ResponseEntity<Client> updateClient(ClientDto updatedClientDto) {

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

}

