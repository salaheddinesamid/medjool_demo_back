package unit_testing;

import com.example.medjool.dto.OrderItemRequestDto;
import com.example.medjool.dto.OrderRequestDto;
import com.example.medjool.model.*;
import com.example.medjool.repository.ClientRepository;
import com.example.medjool.repository.OrderRepository;
import com.example.medjool.repository.PalletRepository;
import com.example.medjool.repository.ProductRepository;
import com.example.medjool.services.implementation.OrderServiceImpl;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @InjectMocks
    private OrderServiceImpl orderService;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private PalletRepository palletRepository;

    @Mock
    private OrderRepository orderRepository;

    @Test
    public void testCreateOrder_Success() {
        // Arrange
        OrderRequestDto orderRequest = new OrderRequestDto();
        orderRequest.setClientName("Fresh Fruits Inc");

        OrderItemRequestDto itemDto = new OrderItemRequestDto();
        itemDto.setProductId("M_EA_B_M"); // Assuming valid product ID
        itemDto.setItemWeight(500.0);
        itemDto.setPalletId(1);
        itemDto.setPricePerKg(2.5);
        itemDto.setPackaging(1);
        itemDto.setNumberOfPallets(1);
        orderRequest.setItems(List.of(itemDto));

        Client client = new Client();
        client.setClientStatus(ClientStatus.ACTIVE);
        client.setCompanyName("Fresh Fruits Inc");

        Product product = new Product();
        product.setProductId("M_EA_B_M");
        product.setTotalWeight(1000.0);

        Pallet pallet = new Pallet();
        pallet.setPreparationTime(5.0);

        when(clientRepository.findByCompanyName("Fresh Fruits Inc")).thenReturn(client);
        when(productRepository.findById("M_EA_B_M")).thenReturn(Optional.of(product));
        when(palletRepository.findById(1)).thenReturn(Optional.of(pallet));
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        ResponseEntity<?> response = orderService.createOrder(orderRequest);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Order has been created successfully.", response.getBody());
    }
}
