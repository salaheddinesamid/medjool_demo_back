package com.example.medjool.unit;

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



    /*

    @Test
    void createOrder_WithValidRequest_ShouldCreateOrder() {
        // Given
        OrderRequestDto request = new OrderRequestDto();
        request.setClientName("ActiveClient");
        request.setItems(List.of(
                new OrderItemRequestDto(1L, "20", "Red", "A", 10.0, 5.0, 2, "Box")
        ));

        Client activeClient = new Client();
        activeClient.setClientStatus(ClientStatus.ACTIVE);

        Product product = new Product();
        product.setProductId(1L);
        product.setTotalWeight(100.0);

        Pallet pallet = new Pallet();
        pallet.setPalletId(1L);

        when(clientRepository.findByCompanyName("ActiveClient")).thenReturn(activeClient);
        when(productRepository.findByCallibreAndColorAndQuality("20", "Red", "A")).thenReturn(product);
        when(palletRepository.findById(1L)).thenReturn(Optional.of(pallet));
        when(orderRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        // When
        OrderResponseDto response = orderService.createOrder(request);

        // Then
        assertNotNull(response);
        assertEquals(50.0, response.getTotalPrice()); // 10kg * 5.0/kg
        assertEquals(10.0, response.getTotalWeight());
        verify(productRepository, times(1)).save(product);
        assertEquals(90.0, product.getTotalWeight()); // 100 - 10
    }

     */


    @Test
    public void ensuresAllOrderItemsAreAvailable(){


    }


    @Test
    public void ensuresInProductionOrdersCanNotBeCancelled(){


    }

}
