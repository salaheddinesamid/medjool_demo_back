package com.example.medjool.controller;

import com.example.medjool.dto.UserDetailsDto;
import com.example.medjool.services.implementation.UserManagementServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserManagementController {

    private final UserManagementServiceImpl userManagementService;

    @Autowired
    public UserManagementController(UserManagementServiceImpl userManagementService) {
        this.userManagementService = userManagementService;
    }
    // Get all users:
    @GetMapping("/get_all")
    public ResponseEntity<List<UserDetailsDto>> getAllUsers() {
        return userManagementService.getAllUsers();
    }

    @PutMapping("/account/activate/{userId}")
    public ResponseEntity<Object> activateUserAccount(@PathVariable long userId) {
        return userManagementService.activateUserAccount(userId);
    }

    @PutMapping("/account/hold/{userId}")
    public ResponseEntity<Object> holdUserAccount(@PathVariable long userId) {
        return userManagementService.holdUserAccount(userId);
    }
}
