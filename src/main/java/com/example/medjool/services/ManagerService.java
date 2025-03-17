package com.example.medjool.services;

import com.example.medjool.dto.BearerToken;
import com.example.medjool.dto.LoginDto;
import com.example.medjool.dto.UserDetailsDto;
import com.example.medjool.model.Manager;
import com.example.medjool.repository.ManageRepository;
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
    private final ManageRepository manageRepository;
    private final JwtUtilities jwtUtilities;

    @Autowired
    public ManagerService(PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager,
                          ManageRepository manageRepository, JwtUtilities jwtUtilities) {
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.manageRepository = manageRepository;
        this.jwtUtilities = jwtUtilities;
    }


    public ResponseEntity<?> authentication(LoginDto loginDto){
        Manager manager = manageRepository.findByEmail(loginDto.getUserName());

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
}
