package com.example.medjool.services;

import com.example.medjool.dto.UserDetailsDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserManagementService {

    ResponseEntity<List<UserDetailsDto>> getAllUsers();

    ResponseEntity<UserDetailsDto> getUserById(Long id);

    ResponseEntity<Object> activateUserAccount(Long id);

    ResponseEntity<Object> holdUserAccount(Long id);

}
