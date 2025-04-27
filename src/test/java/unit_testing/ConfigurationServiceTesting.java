package unit_testing;

import com.example.medjool.dto.AddressDto;
import com.example.medjool.dto.ClientDto;
import com.example.medjool.dto.ContactDto;
import com.example.medjool.model.Address;
import com.example.medjool.model.Client;
import com.example.medjool.repository.AddressRepository;
import com.example.medjool.repository.ClientRepository;
import com.example.medjool.repository.ContactRepository;
import com.example.medjool.services.implementation.ConfigurationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class ConfigurationServiceTesting {


    @Mock
    private ClientRepository clientRepository;

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private ContactRepository contactRepository;

    @InjectMocks
    private ConfigurationServiceImpl configurationService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateNewClient_AlreadyExists(){
        ClientDto clientDto = new ClientDto(
                "Client1",
                "GM",
                "Export and Import",
                "www.client1.com",
                null,
                null,
                "ACTIVE"
        );


        Client existedClient = new Client();
        when(clientRepository.findByCompanyName(
                clientDto.getCompanyName()
        )).thenReturn(existedClient);

        // Call the service method

        ResponseEntity<Object> response = configurationService.addClient(clientDto);


        assertEquals(409, response.getStatusCodeValue());
        assertEquals("Client already exists", response.getBody());

    }


    @Test
    void createNewClient(){
        ClientDto clientDto = new ClientDto(
                "Client1",
                "GM",
                "Export and Import",
                "www.client1.com",
                null,
                null,
                "ACTIVE"
        );

        ContactDto contactDto = new ContactDto(
                "Purchase",
                "client1@gmail.com",
                "079082"
        );
        AddressDto addressDto = new AddressDto(
                "Street 1",
                "City",
                "State",
                "2300",
                ""
        );

        List<AddressDto> clientAddresses = List.of(addressDto);
        List<ContactDto> clientContacts = List.of(contactDto);

        clientDto.setAddresses(clientAddresses);
        clientDto.setContacts(clientContacts);

        when(clientRepository.findByCompanyName(
                clientDto.getCompanyName()
        )).thenReturn(null);

        // Call the service method
        ResponseEntity<Object> response = configurationService.addClient(clientDto);


        // Verification
        assertEquals(201, response.getStatusCodeValue());
    }


}
