package com.example.medjool.unit;

import com.example.medjool.dto.OrderRequestDto;
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


    @Test
    public void ensuresOrderIsMadeByActiveClient(){
        OrderRequestDto orderRequestDto = new OrderRequestDto(

        );

    }

}
