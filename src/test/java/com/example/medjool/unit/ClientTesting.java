package com.example.medjool.unit;


import com.example.medjool.dto.AddressDto;
import com.example.medjool.dto.ClientDto;
import com.example.medjool.model.Address;
import com.example.medjool.model.Client;
import com.example.medjool.model.Contact;
import com.example.medjool.repository.AddressRepository;
import com.example.medjool.repository.ClientRepository;
import com.example.medjool.repository.ContactRepository;
import com.example.medjool.services.ClientService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
class ClientTesting {

    @InjectMocks
    private ClientService clientService; // Your service class that contains the addClient method

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private ContactRepository contactRepository;

    @Mock
    private ClientRepository clientRepository;

    Logger logger = Logger.getLogger(ClientTesting.class.getName());

    /*
    @Test
    void testAddClient() {
        // Prepare the input data
        ClientDto clientDto = new ClientDto();
        clientDto.setNameOfCompany("Test Company");
        clientDto.setNameOfGeneralManager("John Doe");
        clientDto.setCompanyActivity("Software Development");
        clientDto.setEmail("john.doe@example.com");
        clientDto.setPhone("123456789");

        AddressDto addressDto = new AddressDto();
        addressDto.setCity("Test City");
        addressDto.setCountry("Test Country");
        addressDto.setStreet("Test Street");
        addressDto.setState("Test State");
        addressDto.setPostalCode("12345");

        clientDto.setAddressDtoList(Collections.singletonList(addressDto));

        // Mock the behavior of the repositories
        Address mockAddress = new Address();
        mockAddress.setCity(addressDto.getCity());
        mockAddress.setCountry(addressDto.getCountry());
        mockAddress.setStreet(addressDto.getStreet());
        mockAddress.setState(addressDto.getState());
        mockAddress.setPostalCode(addressDto.getPostalCode());
        when(addressRepository.save(any(Address.class))).thenReturn(mockAddress);

        Contact mockContact = new Contact();
        mockContact.setEmail(clientDto.getEmail());
        mockContact.setPhone(clientDto.getPhone());
        when(contactRepository.save(any(Contact.class))).thenReturn(mockContact);

        Client mockClient = new Client();
        mockClient.setCompanyName(clientDto.getNameOfCompany());
        mockClient.setCompanyActivity(clientDto.getCompanyActivity());
        mockClient.setGeneralManager(clientDto.getNameOfGeneralManager());
        mockClient.setAddresses(Collections.singletonList(mockAddress));
        mockClient.setContacts(Collections.singletonList(mockContact));
        when(clientRepository.save(any(Client.class))).thenReturn(mockClient);

        // Call the method to test
        ResponseEntity<Object> response = clientService.addClient(clientDto);

        // Assertions
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody() instanceof Client);
        Client savedClient = (Client) response.getBody();
        assertEquals(clientDto.getNameOfCompany(), savedClient.getCompanyName());
        assertEquals(clientDto.getEmail(), savedClient.getContacts().get(0).getEmail());
        assertEquals(clientDto.getPhone(), savedClient.getContacts().get(0).getPhone());
        assertEquals(clientDto.getAddressDtoList().size(), savedClient.getAddresses().size());
    }


     */



    /*
    @Test
    void getAllClients() {
        List<Client> clients = clientService.getAllClients();
        logger.info(clients.toString());
        assertNotNull(clients);
    }


     */

}
