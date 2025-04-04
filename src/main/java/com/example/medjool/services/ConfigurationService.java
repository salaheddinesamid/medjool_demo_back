package com.example.medjool.services;

import com.example.medjool.dto.AddressDto;
import com.example.medjool.dto.ClientDto;
import com.example.medjool.dto.ContactDto;
import com.example.medjool.dto.PalletDto;
import com.example.medjool.model.Address;
import com.example.medjool.model.Client;
import com.example.medjool.model.Contact;
import com.example.medjool.model.Pallet;
import com.example.medjool.repository.AddressRepository;
import com.example.medjool.repository.ClientRepository;
import com.example.medjool.repository.ContactRepository;
import com.example.medjool.repository.PalletRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ConfigurationService {

    private final ClientRepository clientRepository;
    private final AddressRepository addressRepository;
    private final ContactRepository contactRepository;
    private final PalletRepository palletRepository;

    public ConfigurationService(ClientRepository clientRepository, AddressRepository addressRepository, ContactRepository contactRepository, PalletRepository palletRepository) {
        this.clientRepository = clientRepository;
        this.addressRepository = addressRepository;
        this.contactRepository = contactRepository;
        this.palletRepository = palletRepository;
    }

    // Return all clients:
    public List<Client> getAllClients(){
        return clientRepository.findAll();
    }


    // Add new client:
    public ResponseEntity<Object> addClient(ClientDto clientDto) {
        Client client = new Client();
        List<Address> clientAddresses = new ArrayList<>();
        List<Contact> clientContacts = new ArrayList<>();

        // Create and save Addresses properly
        for (AddressDto addressDto : clientDto.getAddresses()) {
            Address address = new Address(); // Create a NEW instance for each address
            address.setCity(addressDto.getCity());
            address.setCountry(addressDto.getCountry());
            address.setStreet(addressDto.getStreet());
            address.setState(addressDto.getState());
            address.setPostalCode(addressDto.getPostalCode());

            // Save the address
            address = addressRepository.save(address);
            clientAddresses.add(address);
        }

        for (ContactDto contactDto : clientDto.getContacts()) {
            // Create and save Contact
            Contact contact = new Contact();
            contact.setEmail(contactDto.getEmail());
            contact.setPhone(contactDto.getPhone());
            contact = contactRepository.save(contact);
            clientContacts.add(contact);
        }
        // Set Client details
        client.setCompanyName(clientDto.getCompanyName());
        client.setAddresses(clientAddresses);
        client.setContacts(clientContacts);
        client.setCompanyActivity(clientDto.getCompanyActivity());
        client.setGeneralManager(clientDto.getGeneralManager());
        client.setStatus(clientDto.getStatus());

        // Save the client
        clientRepository.save(client);

        return new ResponseEntity<>(client, HttpStatus.CREATED);
    }

    public ResponseEntity<List<Client>> getAll(){
        List<Client> clients = clientRepository.findAll();
        return new ResponseEntity<>(clients,HttpStatus.OK);
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

        // Dimensions:
        pallet.setX(palletDto.getX());
        pallet.setY(palletDto.getY());


        pallet.setPackaging(palletDto.getPackaging());
        pallet.setTag(palletDto.getTag());
        palletRepository.save(pallet);

        return ResponseEntity.ok().body(pallet);
    }


    public ResponseEntity<List<Pallet>> getAllPallets(){
        List<Pallet> pallets = palletRepository.findAll();
        return ResponseEntity.ok().body(pallets);
    }
}
