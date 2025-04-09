package com.example.medjool.unit;

import com.example.medjool.dto.OrderRequestDto;
import com.example.medjool.exception.ClientNotActiveException;
import com.example.medjool.model.Client;
import com.example.medjool.model.ClientStatus;
import com.example.medjool.repository.ClientRepository;
import com.example.medjool.repository.OrderRepository;
import com.example.medjool.repository.PalletRepository;
import com.example.medjool.repository.ProductRepository;
import com.example.medjool.services.implementation.OrderServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class OrderServiceUnitTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private PalletRepository palletRepository;

    @InjectMocks
    private OrderServiceImpl orderService;



    // To be completed...

    @Test
    void testCreateOrder_ClientNotActive() {
        // Arrange
        OrderRequestDto request = new OrderRequestDto();
        request.setClientName("XYZ Inc");

        Client client = new Client();
        client.setClientStatus(ClientStatus.INACTIVE);

        when(clientRepository.findByCompanyName("Mafriq Limited")).thenReturn(client);

        // Assert + Act
        assertThrows(ClientNotActiveException.class, () -> {
            orderService.createOrder(request);
        });
    }

    @Test
    public void ensuresAllOrderItemsAreAvailable(){


    }


    @Test
    public void ensuresInProductionOrdersCanNotBeCancelled(){


    }

}
