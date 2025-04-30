package unit_testing;


import com.example.medjool.repository.OrderRepository;
import com.example.medjool.repository.ProductRepository;
import com.example.medjool.repository.SystemSettingRepository;
import com.example.medjool.services.implementation.AlertServiceImpl;
import com.example.medjool.services.implementation.OverviewServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class OverviewServiceTesting {

    @Mock
    private ProductRepository productRepository;

    @Mock
    OrderRepository orderRepository;

    @Mock
    SystemSettingRepository systemSettingRepository;

    @Mock
    private AlertServiceImpl alertService;

    @InjectMocks
    private OverviewServiceImpl overviewService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }



    @Test
    void getAnOverview(){

    }
}
