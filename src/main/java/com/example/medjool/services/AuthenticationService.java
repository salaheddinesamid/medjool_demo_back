package com.example.medjool.services;

import com.example.medjool.dto.LoginRequestDto;
import com.example.medjool.dto.NewUserDto;
import org.springframework.http.ResponseEntity;

public interface AuthenticationService {

    ResponseEntity<?> authenticate(LoginRequestDto loginRequestDto);
    ResponseEntity<?> createCredentials(NewUserDto newUserDto);
    ResponseEntity<Object> logout(String token);
}
