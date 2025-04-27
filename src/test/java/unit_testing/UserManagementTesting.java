package unit_testing;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(
        classes = {UserManagementTesting.class},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserManagementTesting {

    @Test
    void test(){

    }

}
