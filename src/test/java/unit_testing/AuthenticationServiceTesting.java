package unit_testing;

import com.example.medjool.dto.AuthenticationResponseDto;
import com.example.medjool.dto.LoginRequestDto;
import com.example.medjool.dto.NewUserDto;
import com.example.medjool.exception.UserAlreadyExistsException;
import com.example.medjool.jwt.JwtUtilities;
import com.example.medjool.model.Role;
import com.example.medjool.model.RoleName;
import com.example.medjool.model.User;
import com.example.medjool.repository.RoleRepository;
import com.example.medjool.repository.UserRepository;
import com.example.medjool.services.implementation.AuthenticationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class AuthenticationServiceTesting {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtUtilities jwtUtilities;

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private AuthenticationServiceImpl authenticationService;

    private PasswordEncoder passwordEncoder;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        passwordEncoder = new BCryptPasswordEncoder();
    }


    @Test
    void registerUser_AlreadyExists() {
        Role role = new Role();
        role.setId(1L);
        role.setRoleName(RoleName.GENERAL_MANAGER);

        NewUserDto newUserDto = new NewUserDto();
        newUserDto.setFirstName("Test");
        newUserDto.setLastName("User");
        newUserDto.setEmail("test@test.com");
        newUserDto.setPassword(passwordEncoder.encode("password"));
        newUserDto.setRoleName("GENERAL_MANAGER");

        when(roleRepository.findByRoleName(RoleName.GENERAL_MANAGER)).thenReturn(Optional.of(role));

        User existedUser = new User();
        when(userRepository.findByEmail(newUserDto.getEmail())).thenReturn(Optional.of(existedUser));

        // Vérifiez que l'exception est levée
        UserAlreadyExistsException exception =
                org.junit.jupiter.api.Assertions.assertThrows(
                        UserAlreadyExistsException.class,
                        () -> authenticationService.createCredentials(newUserDto)
                );

        assertEquals("User already exists", exception.getMessage());
    }


    @Test
    void createCredentials() {


    }

    @Test
    void testAuthenticate_Success() {
        // Arrange
        Role role = new Role();
        role.setId(1L);
        role.setRoleName(RoleName.GENERAL_MANAGER);

        LoginRequestDto loginRequestDto = new LoginRequestDto();
        loginRequestDto.setEmail("test@test.com");
        loginRequestDto.setPassword("password");

        User existedUser = new User();
        existedUser.setFirstName("Test");
        existedUser.setLastName("User");
        existedUser.setEmail("test@test.com");
        existedUser.setPassword(passwordEncoder.encode("password")); // encode password!
        existedUser.setAccountNonLocked(true);
        existedUser.setRole(role); // Important: Set the role here!

        when(userRepository.findByEmail(existedUser.getEmail())).thenReturn(Optional.of(existedUser));

        // Act
        ResponseEntity<?> response = authenticationService.authenticate(loginRequestDto);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody() instanceof AuthenticationResponseDto);

        AuthenticationResponseDto responseDto = (AuthenticationResponseDto) response.getBody();

        assertNotNull(responseDto.getToken());
        assertNotNull(responseDto.getUser());
        assertEquals("Test", responseDto.getUser().getFirstName());
        assertEquals("User", responseDto.getUser().getLastName());
        assertEquals("test@test.com", responseDto.getUser().getEmail());
        assertEquals("GENERAL_MANAGER", responseDto.getUser().getRole());
    }

    @Test
    void authenticateUser_InvalidCredentials(){

    }

    @Test
    void authenticatedUser_notExists(){

    }


}
