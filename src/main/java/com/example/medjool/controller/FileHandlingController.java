package com.example.medjool.controller;

/**
import com.example.medjool.services.FileHandling;
import com.example.medjool.services.ParseCSV;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/file/")
public class FileHandlingController {
    private final FileHandling fileHandling;
    private final ParseCSV parseCSV;

    public FileHandlingController(FileHandling fileHandling, ParseCSV parseCSV) {
        this.fileHandling = fileHandling;
        this.parseCSV = parseCSV;
    }


    @PostMapping(value = "stock/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateStock(@RequestParam("file")MultipartFile multipartFile){

        if (multipartFile.isEmpty()) {
            return ResponseEntity.badRequest().body("Please upload a CSV file.");
        }

        return parseCSV.parseCSV(multipartFile);
    }

}
**/