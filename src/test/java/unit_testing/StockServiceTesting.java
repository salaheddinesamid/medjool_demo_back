package unit_testing;

import com.example.medjool.dto.NewProductDto;
import com.example.medjool.model.Product;
import com.example.medjool.repository.ProductRepository;
import com.example.medjool.services.implementation.StockServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class StockServiceTesting {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private StockServiceImpl stockService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateNewProduct_ProductAlreadyExists() {

        NewProductDto newProductDto = new NewProductDto();
        newProductDto.setCallibre("BBS");
        newProductDto.setColor("Dark");
        newProductDto.setQuality("Export A");
        newProductDto.setFarm("Medjool");

        Product existingProduct = new Product();
        when(productRepository.findByCallibreAndColorAndQualityAndFarm(
                "BBS", "Dark", "Export A", "Medjool")).thenReturn(existingProduct);

        // Call the service method
        ResponseEntity<Object> response = stockService.createNewProduct(newProductDto);

        // Verification
        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Product already exists", response.getBody());
        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    void testCreateNewProduct_Success() {

        NewProductDto newProductDto = new NewProductDto();
        newProductDto.setCallibre("A");
        newProductDto.setColor("Red");
        newProductDto.setQuality("High");
        newProductDto.setFarm("Farm1");
        newProductDto.setTotalWeight(100.0);

        when(productRepository.findByCallibreAndColorAndQualityAndFarm(
                "A", "Red", "High", "Farm1")).thenReturn(null);


        ResponseEntity<Object> response = stockService.createNewProduct(newProductDto);

        // Vérifications
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("New product created successfully", response.getBody());
        verify(productRepository, times(1)).save(any(Product.class));
    }
}