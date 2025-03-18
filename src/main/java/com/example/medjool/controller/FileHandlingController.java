package com.example.medjool.controller;

import com.example.medjool.services.FileHandling;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/file/")
public class FileHandlingController {
    private final FileHandling fileHandling;

    public FileHandlingController(FileHandling fileHandling) {
        this.fileHandling = fileHandling;
    }

}
