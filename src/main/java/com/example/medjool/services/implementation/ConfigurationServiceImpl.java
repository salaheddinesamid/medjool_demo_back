package com.example.medjool.services.implementation;

import com.example.medjool.dto.*;
import com.example.medjool.exception.ClientAlreadyFoundException;
import com.example.medjool.model.*;
import com.example.medjool.repository.AddressRepository;
import com.example.medjool.repository.ClientRepository;
import com.example.medjool.repository.ContactRepository;
import com.example.medjool.repository.PalletRepository;
import com.example.medjool.services.ConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ConfigurationServiceImpl implements ConfigurationService {
    private final ClientRepository clientRepository;
    private final AddressRepository addressRepository;
    private final ContactRepository contactRepository;
    private final PalletRepository palletRepository;

    @Autowired
    public ConfigurationServiceImpl(ClientRepository clientRepository, AddressRepository addressRepository, ContactRepository contactRepository, PalletRepository palletRepository) {
        this.clientRepository = clientRepository;
        this.addressRepository = addressRepository;
        this.contactRepository = contactRepository;
        this.palletRepository = palletRepository;
    }
    // Add new client:
    @Override
    public ResponseEntity<Object> addClient(ClientDto clientDto) {


        if(clientRepository.findByCompanyName(clientDto.getCompanyName())!=null){
            throw new ClientAlreadyFoundException();
        }

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
            contact.setDepartment(contactDto.getDepartment());
            contact = contactRepository.save(contact);
            clientContacts.add(contact);
        }
        // Set Client details
        client.setCompanyName(clientDto.getCompanyName());
        client.setAddresses(clientAddresses);
        client.setContacts(clientContacts);
        client.setCompanyActivity(clientDto.getCompanyActivity());
        client.setGeneralManager(clientDto.getGeneralManager());
        client.setClientStatus(ClientStatus.valueOf(clientDto.getStatus()));

        // Save the client
        clientRepository.save(client);

        return new ResponseEntity<>(client, HttpStatus.CREATED);
    }


    @Override
    public ResponseEntity<List<Client>> getAll(){
        List<Client> clients = clientRepository.findAll();
        return new ResponseEntity<>(clients,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> deleteClient(Integer id) throws ClassNotFoundException {
        Optional<Client> client = clientRepository.findById(id);


        if (client.isEmpty()) {
            throw new ClassNotFoundException();
        }
        clientRepository.delete(client.get());
        return new ResponseEntity<>(client, HttpStatus.OK);
    }

    private List<AddressResponseDto> convertToAddressDto(List<Address> addresses) {
        return
                addresses.stream().map(address -> {
                    AddressResponseDto addressResponseDto = new AddressResponseDto();
                    addressResponseDto.setCity(address.getCity());
                    addressResponseDto.setCountry(address.getCountry());
                    addressResponseDto.setStreet(address.getStreet());
                    addressResponseDto.setState(address.getState());
                    return addressResponseDto;
                }).toList();
    }


    @Override
    public ResponseEntity<List<AddressResponseDto>> getClientAddresses(Integer id){
        List<Address> addresses = clientRepository.findById(id).get().getAddresses();

        List<AddressResponseDto> addressResponseDtos = convertToAddressDto(addresses);

        return new ResponseEntity<>(addressResponseDtos,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<AddressResponseDto>> getClientAddressesByClientName(String clientName) {
        List<Address> addresses = clientRepository.findByCompanyName(clientName).getAddresses();

        List<AddressResponseDto> addressResponseDtos =
                convertToAddressDto(addresses);
        return new ResponseEntity<>(addressResponseDtos,HttpStatus.OK);
    }


    // -------------------------- Pallet Configuration Service ---------------------------
    @Override
    public ResponseEntity<Object> addPallet(PalletDto palletDto) {
        Pallet pallet = palletRepository.findByPackagingAndDimensions(palletDto.getPackaging(),palletDto.getDimensions());

        if(pallet != null){
            return new ResponseEntity<>("Pallet already exists", HttpStatus.CONFLICT);
        }
        Pallet newPallet = new Pallet();
        newPallet.setNumberOfStoriesInPallet(
                palletDto.getNumberOfStoriesInPallet()
        );
        newPallet.setNumberOfBoxesInCarton(
                palletDto.getNumberOfBoxesInCarton()
        );

        newPallet.setNumberOfCartonsInStory(
                palletDto.getNumberOfCartonsInStory()
        );
        // Dimensions:
        newPallet.setDimensions(
                palletDto.getDimensions()
        );
        // Preparation hours:
        newPallet.setPreparationTime(palletDto.getPreparationTime());
        newPallet.setPackaging(palletDto.getPackaging());
        newPallet.setTag(palletDto.getTag());
        newPallet.setTotalNet(
                palletDto.getTotalNet()
        );
        palletRepository.save(newPallet);
        return ResponseEntity.ok().body(newPallet);
    }



    @Override
    public ResponseEntity<List<Pallet>> getAllPallets(){
        List<Pallet> pallets = palletRepository.findAll();
        return ResponseEntity.ok().body(pallets);
    }

    @Override
    public ResponseEntity<List<Pallet>> getAllPalletsByPackaging(float packaging) {
        List<Pallet> pallets = palletRepository.findAllByPackaging(
                packaging
        );

        return ResponseEntity.ok().body(pallets);
    }


    public ResponseEntity<Object> deletePallet(Integer palletId) {
        Pallet pallet = palletRepository.findById(palletId).orElseThrow(null);
        palletRepository.delete(pallet);
        return ResponseEntity.ok().body(pallet);
    }
}
