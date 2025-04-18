package com.example.medjool.services.implementation;

import com.example.medjool.dto.BearerTokenDto;
import com.example.medjool.dto.LoginRequestDto;
import com.example.medjool.dto.NewUserDto;
import com.example.medjool.jwt.JwtUtilities;
import com.example.medjool.model.Role;
import com.example.medjool.model.RoleName;
import com.example.medjool.model.User;
import com.example.medjool.repository.RoleRepository;
import com.example.medjool.repository.UserRepository;
import com.example.medjool.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final JwtUtilities jwtUtilities;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Autowired
    public AuthenticationServiceImpl(UserRepository userRepository, JwtUtilities jwtUtilities, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.jwtUtilities = jwtUtilities;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }


    @Override
    public ResponseEntity authenticate(LoginRequestDto loginRequestDto) {
        Optional<User> optionalUser = userRepository.findByEmail(loginRequestDto.getEmail());

        if (optionalUser.isEmpty()) {
            return new ResponseEntity<Object>("Invalid user", HttpStatus.UNAUTHORIZED);
        }

        User user = optionalUser.get();

        if (!passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword()) && user.isAccountNonLocked()) {
            return new ResponseEntity("Invalid credentials", HttpStatus.UNAUTHORIZED);
        }

        LocalDateTime now = LocalDateTime.now();
        String token = jwtUtilities.generateToken(user.getEmail(), user.getRole().getRoleName().toString());
        BearerTokenDto bearerTokenDto = new BearerTokenDto(token);
        user.setLastLogin(now);
        userRepository.save(user);

        return new ResponseEntity<>(bearerTokenDto, HttpStatus.OK);
    }


    public ResponseEntity<Object> createCredentials(NewUserDto newUserDto){


        // Check if the user already exists:
        Optional<User> user = userRepository.findByEmail(newUserDto.getEmail());

        user.ifPresent(u->{
            throw new RuntimeException("User already exists");
        });

        // Create new user:
        User newUser = new User();

        RoleName roleName = RoleName.valueOf(newUserDto.getRoleName());
        Role role = roleRepository.findByRoleName(roleName).orElseThrow(() -> new RuntimeException("Role not found"));

        newUser.setFirstName(newUserDto.getFirstName());
        newUser.setLastName(newUserDto.getLastName());
        newUser.setEmail(newUserDto.getEmail());
        newUser.setRole(role);
        newUser.setPassword(passwordEncoder.encode(newUserDto.getPassword()));
        newUser.setAccountNonLocked(false);
        newUser.setEnabled(true);
        userRepository.save(newUser);

        return new ResponseEntity<>("NEW USER CREATED, WAITING FOR UNLOCKING THE ACCOUNT BY AN ADMIN...", HttpStatus.CREATED);
    }
}
