package com.example.medjool.unit;

import com.example.medjool.dto.AddressDto;
import com.example.medjool.dto.ClientDto;
import com.example.medjool.dto.ContactDto;
import com.example.medjool.model.Address;
import com.example.medjool.model.Contact;
import com.example.medjool.repository.AddressRepository;
import com.example.medjool.repository.ClientRepository;
import com.example.medjool.repository.ContactRepository;
import com.example.medjool.repository.PalletRepository;
import com.example.medjool.services.implementation.ConfigurationServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class ConfigurationServiceTesting {

    @Mock
    private PalletRepository palletRepository;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private ContactRepository contactRepository;

    @InjectMocks
    private ConfigurationServiceImpl configurationService;


    // Ensures that each client is created once, and dont allow for duplicates.
    @Test
    public void shouldNotCreateDuplicateClient(){

        // Create new client:
        ClientDto client = new ClientDto();

        List<ContactDto> contactsDto = new ArrayList<>();
        List<Contact> contacts = new ArrayList<>();

        List<AddressDto> addressesDto = new ArrayList<>();
        List<Address> addresses = new ArrayList<>();

        // Create new contact:
        ContactDto contactDto = new ContactDto();
        contactDto.setDepartment("Purchase");
        contactDto.setPhone("00000");
        contactDto.setEmail("contact@example.com");



        // Create new address:
        AddressDto addressDto = new AddressDto();
        addressDto.setCountry("USA");
        addressDto.setCity("LA");
        addressDto.setState("CA");
        addressDto.setStreet("123 Main Street");


        client.setCompanyName("ALPHA");
        client.setCompanyActivity("Import Export");
        client.setStatus("ACTIVE");
        client.setGeneralManager("John Doe");
        client.setContacts(contactsDto);
        client.setAddresses(addressesDto);



    }
}
