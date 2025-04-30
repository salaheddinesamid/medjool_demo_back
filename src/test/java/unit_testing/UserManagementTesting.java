package unit_testing;

import com.example.medjool.repository.UserRepository;
import com.example.medjool.services.implementation.UserManagementServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

public class UserManagementTesting {


    @Mock
    private UserRepository userRepository;


    @InjectMocks
    private UserManagementServiceImpl userManagementService;


    @Test
    void test(){

    }

}
