package com.example.medjool.services;

import com.example.medjool.dto.BearerTokenDto;
import com.example.medjool.dto.LoginRequestDto;
import com.example.medjool.dto.NewUserDto;
import org.springframework.http.ResponseEntity;

public interface AuthenticationService {

    ResponseEntity<BearerTokenDto> authenticate(LoginRequestDto loginRequestDto);
    ResponseEntity<Object> createCredentials(NewUserDto newUserDto);

}
