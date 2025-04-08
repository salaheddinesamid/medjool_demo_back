package com.example.medjool.unit;

import com.example.medjool.dto.AddressDto;
import com.example.medjool.dto.ClientDto;
import com.example.medjool.dto.ContactDto;
import com.example.medjool.model.Address;
import com.example.medjool.model.Client;
import com.example.medjool.model.Contact;
import com.example.medjool.repository.AddressRepository;
import com.example.medjool.repository.ClientRepository;
import com.example.medjool.repository.ContactRepository;
import com.example.medjool.services.implementation.ConfigurationServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;

import java.util.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ClientServiceUnitTest {

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private ContactRepository contactRepository;

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ConfigurationServiceImpl configurationService;

    @Test
    public void whenAddClient_thenClientIsSavedWithAddressesAndContacts() {
        // Given
        AddressDto addressDto = new AddressDto("123 Main St", "Springfield", "IL", "USA", "62704");
        ContactDto contactDto = new ContactDto("sales@example.com", "+123456789", "Sales");
        ClientDto clientDto = new ClientDto(
                "Acme Corp",
                "Manufacturing",
                "John Doe",
                "www.acme.com",
                List.of(contactDto),
                List.of(addressDto),
                "ACTIVE"
        );

        // Mock repository responses
        Address savedAddress = new Address(1L, "123 Main St", "Springfield", "IL", "USA", "62704");
        Contact savedContact = new Contact(1, "sales@example.com", "+123456789", "Sales");
        Client savedClient = new Client();
        savedClient.setClientId(1);
        savedClient.setCompanyName("Acme Corp");
        savedClient.setAddresses(List.of(savedAddress));
        savedClient.setContacts(List.of(savedContact));

        when(addressRepository.save(any(Address.class))).thenReturn(savedAddress);
        when(contactRepository.save(any(Contact.class))).thenReturn(savedContact);
        when(clientRepository.save(any(Client.class))).thenReturn(savedClient);

        // When
        ResponseEntity<Object> response = configurationService.addClient(clientDto);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Client returnedClient = (Client) response.getBody();
        assertThat(returnedClient.getCompanyName()).isEqualTo("Acme Corp");
        assertThat(returnedClient.getAddresses()).hasSize(1);
        assertThat(returnedClient.getContacts()).hasSize(1);

        // Verify interactions
        verify(addressRepository, times(1)).save(any(Address.class));
        verify(contactRepository, times(1)).save(any(Contact.class));
        verify(clientRepository, times(1)).save(any(Client.class));
    }

    /* To be implemented...
    @Test
    public void whenAddingDuplicateClient_thenShouldReturnConflict() {
        // Given
        AddressDto addressDto = new AddressDto("123 Main St", "Springfield", "IL", "USA", "62704");
        ContactDto contactDto = new ContactDto("sales@example.com", "+123456789", "Sales");

        ClientDto clientDto = new ClientDto(
                "Acme Corp",
                "Manufacturing",
                "John Doe",
                "www.acme.com",
                List.of(contactDto),
                List.of(addressDto),
                "ACTIVE"
        );

        // Mock repository responses
        when(clientRepository.findByCompanyName("Acme Corp"))
                .thenReturn(null)  // First call returns false (not exists)
                .thenReturn(Client.class);  // Second call returns true (exists)

        Address savedAddress = new Address(1L, "123 Main St", "Springfield", "IL", "USA", "62704");
        Contact savedContact = new Contact(1, "sales@example.com", "+123456789", "Sales");
        Client savedClient = new Client();
        savedClient.setClientId(1);
        savedClient.setCompanyName("Acme Corp");
        savedClient.setAddresses(List.of(savedAddress));
        savedClient.setContacts(List.of(savedContact));

        when(addressRepository.save(any())).thenReturn(savedAddress);
        when(contactRepository.save(any())).thenReturn(savedContact);
        when(clientRepository.save(any())).thenReturn(savedClient);

        // When - First attempt (should succeed)
        ResponseEntity<Object> response1 = configurationService.addClient(clientDto);

        // Then - Verify first creation
        assertThat(response1.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        // When - Second attempt (should fail)
        ResponseEntity<Object> response2 = configurationService.addClient(clientDto);

        // Then - Verify duplicate prevention
        assertThat(response2.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
        assertThat(response2.getBody()).isEqualTo("Client with this company name and website already exists");
    }

     */
}