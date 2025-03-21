package com.example.medjool.controller;


import com.example.medjool.dto.ClientDto;
import com.example.medjool.model.Client;
import com.example.medjool.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/client")
public class ClientController {

    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }


    @PostMapping("new")
    public ResponseEntity<Object> addNewClient(@RequestBody ClientDto client) {
        return clientService.addClient(client);
    }

    @GetMapping("get_all")
    public ResponseEntity<List<Client>> getAllClients() {
        return clientService.getAll();
    }
}
