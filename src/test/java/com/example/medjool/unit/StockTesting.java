package com.example.medjool.unit;

import com.example.medjool.dto.OverviewDto;
import com.example.medjool.repository.OrderRepository;
import com.example.medjool.repository.ProductRepository;
import com.example.medjool.services.OverviewService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StockTesting {

    @InjectMocks
    private OverviewService overviewService;

    @Mock
    private  ProductRepository productRepository;

    @Mock
    private OrderRepository orderRepository;



    // Get total weight of the stock:
    @Test
    void getAllProducts() {
        ResponseEntity<OverviewDto> overview = overviewService.getOverview();
        Double totalStockWeight = Objects.requireNonNull(overview.getBody()).getTotalStock();
        assertEquals(totalStockWeight, 21835 );
    }
}
