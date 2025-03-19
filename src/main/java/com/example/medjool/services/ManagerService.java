package com.example.medjool.services;
/**
import com.example.medjool.dto.BearerToken;
import com.example.medjool.dto.LoginDto;
import com.example.medjool.dto.UserDetailsDto;
import com.example.medjool.model.Manager;

import com.example.medjool.repository.ManagerRepository;
import com.example.medjool.repository.RoleRepository;
import com.example.medjool.security.JwtUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ManagerService {

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final ManagerRepository managerRepository;
    private final JwtUtilities jwtUtilities;
    private final RoleRepository roleRepository;

    @Autowired
    public ManagerService(PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager,
                          ManagerRepository manageRepository, JwtUtilities jwtUtilities, RoleRepository roleRepository) {
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.managerRepository = manageRepository;
        this.jwtUtilities = jwtUtilities;
        this.roleRepository = roleRepository;
    }



    public ResponseEntity<?> register(RegistrationDto registrationDto){

        if(!managerRepository.existsByEmail(registrationDto.getEmail())){
            Manager manager = new Manager();
            manager.setFirstName(registrationDto.getFirstName());
            manager.setLastName(registrationDto.getLastName());
            manager.setEmail(registrationDto.getEmail());
            manager.
            Role role = roleRepository.findRoleByRoleName("MANAGER");
            manager.setRole(role);

            String token = jwtUtilities.generateToken(manager.getEmail(),manager.getRole().getRoleName());
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken()
            )
        }
    }



    public ResponseEntity<?> authentication(LoginDto loginDto){
        Manager manager = managerRepository.findByEmail(loginDto.getUserName());

        if(manager.getPassword().equals(passwordEncoder.encode(loginDto.getPassword()))){
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getUserName(),loginDto.getPassword())
            );

            UserDetailsDto userDetailsDto = new UserDetailsDto();
            String token = jwtUtilities.generateToken(manager.getEmail(),manager.getRole().getRoleName());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            userDetailsDto
                    .setBearerToken(new BearerToken(token));

            userDetailsDto.setFirstName(manager.getFirstName());

            return new ResponseEntity<>(userDetailsDto,HttpStatus.OK);

        }else{
            return new ResponseEntity<>("Authentication Failed", HttpStatus.NOT_FOUND);
        }
    }
}**/