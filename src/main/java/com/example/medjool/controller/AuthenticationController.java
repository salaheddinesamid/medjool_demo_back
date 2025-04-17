package com.example.medjool.controller;

import com.example.medjool.dto.LoginRequestDto;
import com.example.medjool.dto.NewUserDto;
import com.example.medjool.services.implementation.AuthenticationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
public class AuthenticationController {

    private final AuthenticationServiceImpl authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationServiceImpl authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequestDto loginRequestDto) {
        return authenticationService.authenticate(loginRequestDto);
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody NewUserDto newUserDto) {
        return authenticationService.createCredentials(newUserDto);
    }

}
