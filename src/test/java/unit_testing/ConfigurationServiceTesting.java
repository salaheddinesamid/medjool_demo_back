package integration_testing;

import com.example.medjool.dto.*;
import com.example.medjool.exception.ClientAlreadyFoundException;
import com.example.medjool.model.*;
import com.example.medjool.repository.AddressRepository;
import com.example.medjool.repository.ClientRepository;
import com.example.medjool.repository.ContactRepository;
import com.example.medjool.repository.PalletRepository;
import com.example.medjool.services.implementation.ConfigurationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class ConfigurationServiceTesting {


    @Mock
    private ClientRepository clientRepository;

    @Mock
    private PalletRepository palletRepository;

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
    void testCreateNewClient_AlreadyExists() throws ClientAlreadyFoundException {
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


        ClientAlreadyFoundException exception =
                org.junit.jupiter.api.Assertions.assertThrows(
                        ClientAlreadyFoundException.class,
                        () -> configurationService.addClient(clientDto)
                );

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


    @Test
    void createPallet_AlreadyExists(){
        PalletDto palletDto = new PalletDto();
        palletDto.setPackaging(1);
        palletDto.setDimensions("180 x 120");
        palletDto.setTag("Standard");
        palletDto.setNumberOfStoriesInPallet(10);
        palletDto.setNumberOfBoxesInCarton(20);
        palletDto.setNumberOfCartonsInStory(2);
        palletDto.setNotes("");

        Pallet existedPallet = new Pallet();
        when(palletRepository.findByPackagingAndDimensions(
                palletDto.getPackaging(),
                palletDto.getDimensions()
        )).thenReturn(existedPallet);

        // Call the service method:

        ResponseEntity<Object> response = configurationService.addPallet(palletDto);

        assertEquals(409, response.getStatusCodeValue());
    }

    @Test
    void getClientAddresses(){

        String companyName = "Client1";

        Address address = new Address();
        address.setCountry("Morocco");
        address.setStreet("Street 1");
        address.setState("State");
        address.setPostalCode("2300");
        address.setCity("City");
        address.setAddressId(1L);

        Contact contact = new Contact();
        contact.setContactId(1);
        contact.setDepartment("D1");
        contact.setEmail("contact@gmail.com");
        contact.setPhone("079082");

        Client existedClient = new Client();
        existedClient.setCompanyName("Client1");
        existedClient.setClientStatus(ClientStatus.ACTIVE);
        existedClient.setAddresses(List.of(address));
        existedClient.setContacts(List.of(contact));

        when(clientRepository.findByCompanyName(companyName)).thenReturn(existedClient);

        // Call the service method:
        ResponseEntity<List<AddressResponseDto>> response = configurationService.getClientAddressesByClientName(companyName);
        assertEquals(1, Objects.requireNonNull(response.getBody()).size());
    }


}
